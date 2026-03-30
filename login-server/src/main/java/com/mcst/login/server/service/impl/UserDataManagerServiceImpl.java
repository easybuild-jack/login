package com.mcst.login.server.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mcst.easyfk.authority.properties.LoginCacheProperties;
import com.mcst.easyfk.authority.vo.UserAuth;
import com.mcst.easyfk.core.builders.BRBuilder;
import com.mcst.easyfk.core.dto.login.LoginUser;
import com.mcst.easyfk.core.dto.login.UserData;
import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.core.utils.common.EmptyUtil;
import com.mcst.easyfk.repository.search.SCBuilder;
import com.mcst.easyfk.repository.search.SearchCondition;
import com.mcst.login.api.dto.LoginCacheDto;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.orm.repository.ILoginCacheRepository;
import com.mcst.login.orm.repository.ILoginRecordRepository;
import com.mcst.login.server.service.IUserDataManagerService;
import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserDataManagerServiceImpl implements IUserDataManagerService {

    private static final long REFRESH_THRESHOLD_SECONDS = 600L;

    private final Cache<String, Long> loginTokenNextCheckCache = Caffeine.newBuilder().maximumSize(100000).expireAfterWrite(10, TimeUnit.HOURS).build();
    private final Cache<String, LoginCacheDto> loginCacheDtoCache = Caffeine.newBuilder().maximumSize(100000).expireAfterWrite(30, TimeUnit.MINUTES).build();

    @Resource
    private ILoginRecordRepository loginRecordRepository;

    @Resource
    private ILoginCacheRepository loginCacheRepository;

    @Resource
    private LoginCacheProperties loginCacheProperties;


    @Override
    public BaseResult<?> loginOut(UserData userData) {
        if (EmptyUtil.isEmpty(userData) || EmptyUtil.isEmpty(userData.getLoginToken())) {
            return BRBuilder.<String>builder(false).build();
        }
        SearchCondition searchCondition = SCBuilder.<LoginCacheDto>builder().equalsConditions(LoginCacheDto::getLoginToken, userData.getLoginToken()).build();
        this.loginCacheRepository.deleteByCondition(searchCondition);
        this.loginCacheDtoCache.invalidate(userData.getLoginToken());
        this.loginTokenNextCheckCache.invalidate(userData.getLoginToken());
        return BRBuilder.<String>builder(true).build();
    }

    @Override
    public BaseResult<Boolean> checkAuth(String loginToken, String path) {
        UserAuth userAuth = this.getUserAuth(loginToken);
        if (null == userAuth) {
            return BRBuilder.<Boolean>builder(false).build();
        }
        List<String> employeeUrls = userAuth.getUrls();
        if (EmptyUtil.isNotEmpty(employeeUrls) && employeeUrls.contains(path)) {
            return BRBuilder.<Boolean>builder(true).data(true).build();
        }
        return BRBuilder.<Boolean>builder(true).data(false).build();
    }

    @Override
    public void refreshUserToken(LoginUser loginUser) {
        String loginToken = loginUser.getLoginToken();
        Long nextCheckTime = this.loginTokenNextCheckCache.getIfPresent(loginToken);
        long now = System.currentTimeMillis();
        if (null != nextCheckTime && now < nextCheckTime) {
            return;
        }
        LocalDateTime exTime = null;
        boolean needRefresh = false;
        LoginCacheDto loginCacheDto = this.loginCacheRepository.queryOneByField("loginToken", loginUser.getLoginToken(), "loginCacheId", "loginToken", "expireTime");
        if (EmptyUtil.isNotEmpty(loginCacheDto)) {
            LocalDateTime oldExTime = loginCacheDto.getExpireTime();
            //比较时间，如果数据库里的过期时间和当前时间比较，小于10分钟则更新
            if (oldExTime != null && oldExTime.isBefore(LocalDateTime.now().plusMinutes(10))) {
                needRefresh = true;
                exTime = LocalDateTime.now().plusMinutes(loginCacheProperties.getLiveTime());
                loginCacheDto.setExpireTime(exTime);
                this.loginCacheRepository.updateBySelective(loginCacheDto);
                this.cacheNextCheckTime(loginToken, loginCacheProperties.getLiveTime() * 60L);
                this.loginCacheDtoCache.put(loginToken, loginCacheDto);
            }
        } else {
            this.loginTokenNextCheckCache.invalidate(loginToken);
            this.loginCacheDtoCache.invalidate(loginToken);
        }
        if (needRefresh) {
            LoginRecordDto loginRecordDto = this.loginRecordRepository.queryOneByField("loginToken", loginUser.getLoginToken(), "loginRecordId", "loginToken", "expireTime");
            if (EmptyUtil.isNotEmpty(loginRecordDto)) {
                loginRecordDto.setExpireTime(exTime);
                this.loginRecordRepository.updateBySelective(loginRecordDto);
            }
        }
    }

    @Override
    public UserAuth getUserAuth(String loginToken) {
        LoginCacheDto loginCacheDto = getLoginCacheDto(loginToken);
        if (null == loginCacheDto || null == loginCacheDto.getUserAuth()) {
            return null;
        }
        return JSON.parseObject(loginCacheDto.getUserAuth(), UserAuth.class);
    }


    @Override
    public BaseResult<String> cacheLoginData(UserData userData, UserAuth userAuth) {
        if (EmptyUtil.isEmpty(userData)) {
            return BRBuilder.<String>builder(false).build();
        }
        String token = IdUtil.fastSimpleUUID();
        userData.setLoginToken(token);
        LoginCacheDto loginCacheDto = new LoginCacheDto();
        loginCacheDto.setLoginToken(token);
        loginCacheDto.setUserId(userData.getUserId());
        loginCacheDto.setType(userData.getType());
        loginCacheDto.setUserData(JSON.toJSONString(userData));
        if (EmptyUtil.isNotEmpty(userAuth)) {
            loginCacheDto.setUserAuth(JSON.toJSONString(userAuth));
        }
        loginCacheDto.setExpireTime(LocalDateTime.now().plusMinutes(loginCacheProperties.getLiveTime()));
        this.loginCacheRepository.insert(loginCacheDto);
        this.cacheNextCheckTime(token, loginCacheProperties.getLiveTime() * 60L);
        this.loginCacheDtoCache.put(token, loginCacheDto);
        return BRBuilder.<String>builder(true).data(token).build();
    }

    @Override
    public UserData getUserData(LoginUser loginUser) {
        LoginCacheDto loginCacheDto = getLoginCacheDto(loginUser.getLoginToken());
        if (null == loginCacheDto || null == loginCacheDto.getUserData()) {
            return null;
        }
        return JSON.parseObject(loginCacheDto.getUserData(), UserData.class);
    }

    @Override
    public BaseResult<?> offLineUser(String userId, String type) {
        SearchCondition condition = SCBuilder.<LoginCacheDto>builder().equalsConditions(LoginCacheDto::getUserId, userId).equalsConditions(LoginCacheDto::getType, type).build();
        List<LoginCacheDto> caches = this.loginCacheRepository.queryByCondition(condition);
        if (EmptyUtil.isNotEmpty(caches)) {
            for (LoginCacheDto cache : caches) {
                this.loginCacheDtoCache.invalidate(cache.getLoginToken());
                this.loginTokenNextCheckCache.invalidate(cache.getLoginToken());
            }
        }
        return this.loginCacheRepository.deleteByCondition(condition);
    }

    private LoginCacheDto getLoginCacheDto(String loginToken) {
        LoginCacheDto loginCacheDto = this.loginCacheDtoCache.getIfPresent(loginToken);
        if (null == loginCacheDto) {
            loginCacheDto = this.loginCacheRepository.queryOneByField("loginToken", loginToken);
            if (null == loginCacheDto) {
                return null;
            }
            if (loginCacheDto.getExpireTime() != null && loginCacheDto.getExpireTime().isBefore(LocalDateTime.now())) {
                return null;
            }
            this.loginCacheDtoCache.put(loginToken, loginCacheDto);
        }
        return loginCacheDto;
    }

    private void cacheNextCheckTime(String loginToken, long expireSeconds) {
        if (EmptyUtil.isEmpty(loginToken)) {
            return;
        }
        long delaySeconds = Math.max(expireSeconds - REFRESH_THRESHOLD_SECONDS, 0L);
        this.loginTokenNextCheckCache.put(loginToken, System.currentTimeMillis() + delaySeconds * 1000L);
    }
}
