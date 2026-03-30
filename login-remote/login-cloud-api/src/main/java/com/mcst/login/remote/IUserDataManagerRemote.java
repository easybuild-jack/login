package com.mcst.login.remote;

import com.mcst.easyfk.authority.vo.UserAuth;
import com.mcst.easyfk.core.dto.login.LoginUser;
import com.mcst.easyfk.core.dto.login.UserData;
import com.mcst.easyfk.core.dto.response.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 登录记录 Remote接口类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
@FeignClient(value = "${easyfk.config.remote.login.service-id:server}", path = "${easyfk.config.remote.login.base-path:/remote}/login/userData")
public interface IUserDataManagerRemote {

    /**
     * 退出登录时清除 UserAuth 缓存
     *
     * @return BaseResult
     */
    @PostMapping("/loginOut")
    BaseResult<?> loginOut(@RequestBody UserData userData);

    /**
     * 检测权限
     *
     * @param loginToken 登录令牌
     * @param path       请求路径
     * @return BaseResult<Boolean>
     */
    @GetMapping("/checkAuth")
    BaseResult<Boolean> checkAuth(@RequestParam("loginToken") String loginToken, @RequestParam("path") String path);

    /**
     * 刷新用户权限缓存时间
     *
     * @param loginUser 登录用户信息
     */
    @PostMapping("/refreshUserToken")
    void refreshUserToken(@RequestBody LoginUser loginUser);

    /**
     * 获取当前用户 UserAuth
     *
     * @param loginToken 缓存键
     * @return UserAuth
     */
    @GetMapping("/getUserAuth")
    UserAuth getUserAuth(@RequestParam("loginToken") String loginToken);

    /**
     * 缓存登录数据
     *
     * @param userData 用户数据
     * @param userAuth 用户认证信息
     * @return BaseResult<String> 返回缓存键
     */
    @PostMapping("/cacheLoginData")
    BaseResult<String> cacheLoginData(@RequestBody UserData userData, @RequestBody UserAuth userAuth);

    /**
     * 获取用户数据
     *
     * @param loginUser 登录用户信息
     * @return UserData
     */
    @PostMapping("/getUserData")
    UserData getUserData(@RequestBody LoginUser loginUser);

    /**
     * 强制下线用户
     *
     * @param userId 用户ID
     * @param type   用户类型
     * @return BaseResult
     */
    @GetMapping("/offLineUser")
    BaseResult<?> offLineUser(@RequestParam("userId") String userId, @RequestParam("type") String type);

}