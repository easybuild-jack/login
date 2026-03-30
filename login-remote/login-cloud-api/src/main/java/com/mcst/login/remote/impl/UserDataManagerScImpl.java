package com.mcst.login.remote.impl;

import com.mcst.easyfk.authority.manager.UserDataManager;
import com.mcst.easyfk.authority.vo.UserAuth;
import com.mcst.easyfk.core.dto.login.LoginUser;
import com.mcst.easyfk.core.dto.login.UserData;
import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.login.remote.IUserDataManagerRemote;
import jakarta.annotation.Resource;

public class UserDataManagerScImpl implements UserDataManager {

    @Resource
    private IUserDataManagerRemote userDataManagerRemote;

    @Override
    public BaseResult<?> loginOut(UserData userData) {
        return this.userDataManagerRemote.loginOut(userData);
    }

    @Override
    public BaseResult<Boolean> checkAuth(String s, String s1) {
        return this.userDataManagerRemote.checkAuth(s, s1);
    }

    @Override
    public void refreshUserToken(LoginUser loginUser) {
        this.userDataManagerRemote.refreshUserToken(loginUser);
    }

    @Override
    public UserAuth getUserAuth(String s) {
        return this.userDataManagerRemote.getUserAuth(s);
    }

    @Override
    public BaseResult<String> cacheLoginData(UserData userData, UserAuth userAuth) {
        return this.userDataManagerRemote.cacheLoginData(userData, userAuth);
    }

    @Override
    public UserData getUserData(LoginUser loginUser) {
        return this.userDataManagerRemote.getUserData(loginUser);
    }

    @Override
    public BaseResult<?> offLineUser(String s, String s1) {
        return this.userDataManagerRemote.offLineUser(s, s1);
    }
}
