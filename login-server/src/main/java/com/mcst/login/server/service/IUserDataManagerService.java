package com.mcst.login.server.service;

import com.mcst.easyfk.authority.vo.UserAuth;
import com.mcst.easyfk.core.dto.login.LoginUser;
import com.mcst.easyfk.core.dto.login.UserData;
import com.mcst.easyfk.core.dto.response.BaseResult;

/**
 * <p>
 * 登录记录 Remote接口类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public interface IUserDataManagerService {

    /**
     * 退出登录时清除 UserAuth 缓存
     *
     * @return BaseResult
     */

    BaseResult<?> loginOut(UserData userData);

    /**
     * 检测权限
     *
     * @param loginToken 登录令牌
     * @param path       请求路径
     * @return BaseResult<Boolean>
     */
    BaseResult<Boolean> checkAuth(String loginToken, String path);

    /**
     * 刷新用户权限缓存时间
     *
     * @param loginUser 登录用户信息
     */
    void refreshUserToken(LoginUser loginUser);

    /**
     * 获取当前用户 UserAuth
     *
     * @param loginToken 缓存键
     * @return UserAuth
     */

    UserAuth getUserAuth(String loginToken);

    /**
     * 缓存登录数据
     *
     * @param userData 用户数据
     * @param userAuth 用户认证信息
     * @return BaseResult<String> 返回缓存键
     */
    BaseResult<String> cacheLoginData(UserData userData, UserAuth userAuth);

    /**
     * 获取用户数据
     *
     * @param loginUser 登录用户信息
     * @return UserData
     */
    UserData getUserData(LoginUser loginUser);

    /**
     * 强制下线用户
     *
     * @param userId 用户ID
     * @param type   用户类型
     * @return BaseResult
     */

    BaseResult<?> offLineUser(String userId, String type);

}