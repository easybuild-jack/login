package com.mcst.login.remote.impl;

import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.remote.sc.BaseApiRemoteImpl;
import com.mcst.login.api.ILoginRecordApi;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.login.api.response.LoginRecordResp;
import com.mcst.login.remote.ILoginRecordRemote;
import jakarta.annotation.Resource;

/**
 * <p>
 * 登录记录 Api接口SC层实现
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public class LoginRecordApiScImpl extends BaseApiRemoteImpl<ILoginRecordRemote, LoginRecordResp, String, LoginRecordReq> implements ILoginRecordApi {

    @Resource
    private ILoginRecordRemote loginRecordRemote;

    @Override
    public BaseResult<?> login(LoginRecordDto loginRecord) {
        return this.loginRecordRemote.login(loginRecord);
    }
}
