package com.mcst.login.remote.impl;

import com.mcst.easyfk.authority.IRefreshUserLoginRecord;
import com.mcst.easyfk.authority.vo.RefreshUserLoginVo;
import com.mcst.login.remote.IRefreshUserLoginRemote;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;

public class RefreshUserLoginDubboImpl implements IRefreshUserLoginRecord {

    @DubboReference
    private IRefreshUserLoginRemote refreshUserLoginRemote;

    @Override
    public void refreshUserLoginRecord(RefreshUserLoginVo refreshUserLoginVo) {
        this.refreshUserLoginRemote.refreshUserLoginRecord(refreshUserLoginVo);
    }
}
