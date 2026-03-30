package com.mcst.login.remote;

import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.easyfk.remote.sc.IBaseRemote;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.login.api.response.LoginRecordResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 登录记录 Remote接口类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
@FeignClient(value = "${easyfk.config.remote.login.service-id:server}", path = "${easyfk.config.remote.login.base-path:/remote}/login/loginRecord")
public interface ILoginRecordRemote extends IBaseRemote<LoginRecordResp, String, LoginRecordReq> {

    /**
     * 登录时登录记录的处理
     *
     * @param loginRecord
     * @return
     */
    @PostMapping("/login")
    BaseResult<?> login(@RequestBody LoginRecordDto loginRecord);
}