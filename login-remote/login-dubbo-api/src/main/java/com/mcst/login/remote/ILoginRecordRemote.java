package com.mcst.login.remote;

import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.api.response.LoginRecordResp;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.easyfk.remote.dubbo.IDubboBaseRemote;

/**
* <p>
* 登录记录 Remote接口类
* </p>
*
* @author eb-jack
* @since 2026-03-30
*/
public interface ILoginRecordRemote extends IDubboBaseRemote<LoginRecordResp,String,LoginRecordReq>{


    /**
     * 登录时登录记录的处理
     *
     * @param loginRecord
     * @return
     */
    BaseResult<?> login(LoginRecordDto loginRecord);

}