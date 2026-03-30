package com.mcst.login.server.service.impl;

import com.mcst.easyfk.core.dto.page.PageResult;
import com.mcst.easyfk.core.dto.request.ModifyRequest;
import com.mcst.easyfk.core.dto.request.SearchRequest;
import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.core.utils.common.EmptyUtil;
import com.mcst.easyfk.core.utils.common.TransformUtil;
import com.mcst.easyfk.repository.search.SCBuilder;
import com.mcst.easyfk.repository.search.SearchCondition;
import com.mcst.easyfk.repository.util.ConditionUtil;
import com.mcst.easyfk.service.util.ServiceUtil;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.login.api.response.LoginRecordResp;
import com.mcst.login.orm.repository.ILoginRecordRepository;
import com.mcst.login.server.service.ILoginRecordService;
import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 登录记录 服务实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */

public class LoginRecordServiceImpl implements ILoginRecordService {
    @Resource
    private ILoginRecordRepository loginRecordRepository;

    @Override
    public LoginRecordResp queryById(String id, String... selectColumns) {
        return TransformUtil.transformObj(this.loginRecordRepository.queryById(id, selectColumns), LoginRecordResp.class);
    }

    @Override
    public LoginRecordResp queryOneByField(String field, Object value, String... selectColumns) {
        return TransformUtil.transformObj(this.loginRecordRepository.queryOneByField(field, value, selectColumns), LoginRecordResp.class);
    }

    @Override
    public List<LoginRecordResp> queryList(SearchRequest<LoginRecordReq> param) {
        return TransformUtil.transformList(this.loginRecordRepository.queryByCondition(ConditionUtil.conditionByRequest(param)), LoginRecordResp.class);
    }

    @Override
    public LoginRecordResp queryOne(SearchRequest<LoginRecordReq> param) {
        return TransformUtil.transformObj(this.loginRecordRepository.queryOneByCondition(ConditionUtil.conditionByRequest(param)), LoginRecordResp.class);
    }

    @Override
    public long count(SearchRequest<LoginRecordReq> param) {
        return this.loginRecordRepository.countByCondition(ConditionUtil.conditionByRequest(param));
    }

    @Override
    public boolean exists(SearchRequest<LoginRecordReq> param) {
        return this.loginRecordRepository.exists(ConditionUtil.conditionByRequest(param));
    }

    @Override
    public PageResult<LoginRecordResp> queryByPage(SearchRequest<LoginRecordReq> param) {
        return TransformUtil.transformPageResult(this.loginRecordRepository.queryByPage(ConditionUtil.conditionByRequest(param)), LoginRecordResp.class);
    }

    @Override
    public BaseResult<?> save(ModifyRequest<LoginRecordReq> param) {
        return ServiceUtil.save(param, loginRecordRepository, LoginRecordDto.class);
    }

    @Override
    public BaseResult<?> delete(ModifyRequest<LoginRecordReq> param) {
        return ServiceUtil.delete(param, loginRecordRepository, LoginRecordDto.class);
    }

    @Override
    public String insertAndReturnId(LoginRecordReq param) {
        return ServiceUtil.insertAndReturnId(param, loginRecordRepository, LoginRecordDto.class);
    }


    @Override
    public BaseResult<?> login(LoginRecordDto userData) {
        //查询上一次登录记录
        SearchCondition searchCondition = SCBuilder.<LoginRecordDto>builder()
                .equalsConditions(LoginRecordDto::getUserId, userData.getUserId())
                .descFields(LoginRecordDto::getLoginTime).top(1)
                .build();
        LoginRecordDto loginRecordDto = this.loginRecordRepository.queryOneByCondition(searchCondition);
        if (loginRecordDto != null && EmptyUtil.isNotEmpty(loginRecordDto.getLoginTime())) {
            userData.setLastLoginTime(loginRecordDto.getLoginTime());
        }
        userData.setLoginTime(LocalDateTime.now());
        return this.loginRecordRepository.insert(userData);
    }
}
