package com.mcst.login.remote.impl;

import com.mcst.easyfk.authority.IRefreshUserLoginRecord;
import com.mcst.easyfk.authority.vo.RefreshUserLoginVo;
import com.mcst.login.remote.IRefreshUserLoginRemote;
import jakarta.annotation.Resource;

public class RefreshUserLoginScImpl implements IRefreshUserLoginRecord {

    @Resource
    private IRefreshUserLoginRemote refreshUserLoginRemote;

    @Override
    public void refreshUserLoginRecord(RefreshUserLoginVo refreshUserLoginVo) {
        this.refreshUserLoginRemote.refreshUserLoginRecord(refreshUserLoginVo);
    }
}
