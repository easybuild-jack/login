package com.mcst.login.server.service.impl;

import com.mcst.easyfk.core.dto.page.PageResult;
import com.mcst.easyfk.core.dto.request.ModifyRequest;
import com.mcst.easyfk.core.dto.request.SearchRequest;
import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.core.utils.common.TransformUtil;
import com.mcst.easyfk.repository.util.ConditionUtil;
import com.mcst.easyfk.service.util.ServiceUtil;
import com.mcst.login.api.dto.LoginCacheDto;
import com.mcst.login.api.request.LoginCacheReq;
import com.mcst.login.api.response.LoginCacheResp;
import com.mcst.login.orm.repository.ILoginCacheRepository;
import com.mcst.login.server.service.ILoginCacheService;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * <p>
 * 登录缓存 服务实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */

public class LoginCacheServiceImpl implements ILoginCacheService {
    @Resource
    private ILoginCacheRepository loginCacheRepository;

    @Override
    public LoginCacheResp queryById(String id, String... selectColumns) {
        return TransformUtil.transformObj(this.loginCacheRepository.queryById(id, selectColumns), LoginCacheResp.class);
    }

    @Override
    public LoginCacheResp queryOneByField(String field, Object value, String... selectColumns) {
        return TransformUtil.transformObj(this.loginCacheRepository.queryOneByField(field, value, selectColumns), LoginCacheResp.class);
    }

    @Override
    public List<LoginCacheResp> queryList(SearchRequest<LoginCacheReq> param) {
        return TransformUtil.transformList(this.loginCacheRepository.queryByCondition(ConditionUtil.conditionByRequest(param)), LoginCacheResp.class);
    }

    @Override
    public LoginCacheResp queryOne(SearchRequest<LoginCacheReq> param) {
        return TransformUtil.transformObj(this.loginCacheRepository.queryOneByCondition(ConditionUtil.conditionByRequest(param)), LoginCacheResp.class);
    }

    @Override
    public long count(SearchRequest<LoginCacheReq> param) {
        return this.loginCacheRepository.countByCondition(ConditionUtil.conditionByRequest(param));
    }

    @Override
    public boolean exists(SearchRequest<LoginCacheReq> param) {
        return this.loginCacheRepository.exists(ConditionUtil.conditionByRequest(param));
    }

    @Override
    public PageResult<LoginCacheResp> queryByPage(SearchRequest<LoginCacheReq> param) {
        return TransformUtil.transformPageResult(this.loginCacheRepository.queryByPage(ConditionUtil.conditionByRequest(param)), LoginCacheResp.class);
    }

    @Override
    public BaseResult<?> save(ModifyRequest<LoginCacheReq> param) {
        return ServiceUtil.save(param, loginCacheRepository, LoginCacheDto.class);
    }

    @Override
    public BaseResult<?> delete(ModifyRequest<LoginCacheReq> param) {
        return ServiceUtil.delete(param, loginCacheRepository, LoginCacheDto.class);
    }

    @Override
    public String insertAndReturnId(LoginCacheReq param) {
        return ServiceUtil.insertAndReturnId(param, loginCacheRepository, LoginCacheDto.class);
    }

}
