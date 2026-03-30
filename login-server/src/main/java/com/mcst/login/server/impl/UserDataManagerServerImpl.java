package com.mcst.login.server.impl;

import com.mcst.easyfk.authority.manager.UserDataManager;
import com.mcst.easyfk.authority.vo.UserAuth;
import com.mcst.easyfk.core.dto.login.LoginUser;
import com.mcst.easyfk.core.dto.login.UserData;
import com.mcst.easyfk.core.dto.response.BaseResult;
import com.mcst.login.server.service.IUserDataManagerService;
import jakarta.annotation.Resource;

/**
 * <p>
 * 登录记录 RemoteImpl
 * </p>
 *
 * @author eb-jack
 */
public class UserDataManagerServerImpl implements UserDataManager {

    @Resource
    private IUserDataManagerService userDataManagerService;

    @Override
    public BaseResult<?> loginOut(UserData userData) {
        return this.userDataManagerService.loginOut(userData);
    }

    @Override
    public BaseResult<Boolean> checkAuth(String loginToken, String path) {
        return this.userDataManagerService.checkAuth(loginToken, path);
    }

    @Override
    public void refreshUserToken(LoginUser loginUser) {
        this.userDataManagerService.refreshUserToken(loginUser);
    }

    @Override
    public UserAuth getUserAuth(String loginToken) {
        return this.userDataManagerService.getUserAuth(loginToken);
    }

    @Override
    public BaseResult<String> cacheLoginData(UserData userData, UserAuth userAuth) {
        return this.userDataManagerService.cacheLoginData(userData, userAuth);
    }

    @Override
    public UserData getUserData(LoginUser loginUser) {
        return this.userDataManagerService.getUserData(loginUser);
    }

    @Override
    public BaseResult<?> offLineUser(String userId, String type) {
        return this.userDataManagerService.offLineUser(userId, type);
    }
}
