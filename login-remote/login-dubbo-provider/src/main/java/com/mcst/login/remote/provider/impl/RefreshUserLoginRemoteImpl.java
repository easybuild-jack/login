package com.mcst.login.remote.provider.impl;

import com.mcst.easyfk.authority.vo.RefreshUserLoginVo;
import com.mcst.login.remote.IRefreshUserLoginRemote;
import com.mcst.login.server.service.IRefreshUserLoginService;
import jakarta.annotation.Resource;
/**
 * <p>
 * 登录记录 RemoteImpl
 * </p>
 *
 * @author eb-jack
 */
public class RefreshUserLoginRemoteImpl implements IRefreshUserLoginRemote {

    @Resource
    private IRefreshUserLoginService refreshUserLoginService;

    @Override
    public void refreshUserLoginRecord(RefreshUserLoginVo refreshUserLoginVo) {
        refreshUserLoginService.refreshUserLoginRecord(refreshUserLoginVo);
    }
}
