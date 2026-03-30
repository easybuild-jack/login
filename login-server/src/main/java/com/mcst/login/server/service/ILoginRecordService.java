package com.mcst.login.server.service;

import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.service.api.IBaseService;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.login.api.response.LoginRecordResp;

/**
 * <p>
 * 登录记录 服务接口类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public interface ILoginRecordService extends IBaseService<LoginRecordResp, String, LoginRecordReq> {


    /**
     * 登录时登录记录的处理
     *
     * @param loginRecord
     * @return
     */
    BaseResult<?> login(LoginRecordDto loginRecord);
}