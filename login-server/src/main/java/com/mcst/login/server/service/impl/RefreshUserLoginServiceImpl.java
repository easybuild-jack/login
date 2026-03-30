package com.mcst.login.server.service.impl;

import com.mcst.easyfk.authority.vo.RefreshUserLoginVo;
import com.mcst.easyfk.core.utils.common.EmptyUtil;
import com.mcst.login.api.dto.LoginCacheDto;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.orm.repository.ILoginCacheRepository;
import com.mcst.login.orm.repository.ILoginRecordRepository;
import com.mcst.login.server.service.IRefreshUserLoginService;
import jakarta.annotation.Resource;

public class RefreshUserLoginServiceImpl implements IRefreshUserLoginService {


    @Resource
    private ILoginRecordRepository loginRecordRepository;

    @Resource
    private ILoginCacheRepository loginCacheRepository;

    @Override
    public void refreshUserLoginRecord(RefreshUserLoginVo refreshUserLoginVo) {
        LoginRecordDto loginRecordDto = this.loginRecordRepository.queryOneByField("loginToken", refreshUserLoginVo.getLoginToke());
        if (EmptyUtil.isNotEmpty(loginRecordDto)) {
            loginRecordDto.setExpireTime(refreshUserLoginVo.getExpireTime());
            this.loginRecordRepository.updateBySelective(loginRecordDto);
        }
        LoginCacheDto loginCacheDto = this.loginCacheRepository.queryOneByField("loginToken", refreshUserLoginVo.getLoginToke()
                , "loginCacheId", "loginToken", "expireTime");
        if (EmptyUtil.isNotEmpty(loginCacheDto)) {
            loginCacheDto.setExpireTime(refreshUserLoginVo.getExpireTime());
            this.loginCacheRepository.updateBySelective(loginCacheDto);
        }
    }
}
