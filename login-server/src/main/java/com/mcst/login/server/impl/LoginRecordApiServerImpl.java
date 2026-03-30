package com.mcst.login.server.impl;

import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.service.base.BaseApiServiceImpl;
import com.mcst.login.api.ILoginRecordApi;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.login.api.response.LoginRecordResp;
import com.mcst.login.server.service.ILoginRecordService;
import jakarta.annotation.Resource;

/**
 * <p>
 * 登录记录 Api接口Server层实现
 * </p>
 *
 * @author eb-jack
 */
public class LoginRecordApiServerImpl extends BaseApiServiceImpl<ILoginRecordService, LoginRecordResp, String, LoginRecordReq> implements ILoginRecordApi {


    @Resource
    private ILoginRecordService loginRecordService;

    @Override
    public BaseResult<?> login(LoginRecordDto loginRecord) {
        return this.loginRecordService.login(loginRecord);
    }
}
