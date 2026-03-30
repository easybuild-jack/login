package com.mcst.login.server.impl;

import com.mcst.easyfk.authority.IRefreshUserLoginRecord;
import com.mcst.easyfk.authority.vo.RefreshUserLoginVo;
import com.mcst.login.server.service.IRefreshUserLoginService;
import jakarta.annotation.Resource;

/**
 * <p>
 * 登录记录 RemoteImpl
 * </p>
 *
 * @author eb-jack
 */
public class RefreshUserLoginServerImpl implements IRefreshUserLoginRecord {

    @Resource
    private IRefreshUserLoginService refreshUserLoginService;

    @Override
    public void refreshUserLoginRecord(RefreshUserLoginVo refreshUserLoginVo) {
        refreshUserLoginService.refreshUserLoginRecord(refreshUserLoginVo);
    }
}
