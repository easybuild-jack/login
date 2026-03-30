package com.mcst.login.remote.provider.impl;

import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.remote.sc.BaseRemoteImpl;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.login.api.response.LoginRecordResp;
import com.mcst.login.remote.ILoginRecordRemote;
import com.mcst.login.server.service.ILoginRecordService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录记录 RemoteImpl
 * </p>
 *
 * @author eb-jack
 */
@RestController
@RequestMapping("${easyfk.config.remote.login.base-path:/remote}/login/loginRecord")
public class LoginRecordRemoteImpl extends BaseRemoteImpl<ILoginRecordService, LoginRecordResp, String, LoginRecordReq> implements ILoginRecordRemote {

    @Resource
    private ILoginRecordService loginRecordService;

    @Override
    public BaseResult<?> login(LoginRecordDto loginRecord) {
        return this.loginRecordService.login(loginRecord);
    }
}
