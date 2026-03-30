package com.mcst.login.remote;

import com.mcst.easyfk.authority.vo.RefreshUserLoginVo;
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
@FeignClient(value = "${easyfk.config.remote.login.service-id:server}", path = "${easyfk.config.remote.login.base-path:/remote}/login/refresh")
public interface IRefreshUserLoginRemote {

    /**
     * 刷新用户登录记录过期时间
     */
    @PostMapping("/refreshUserLogin")
    void refreshUserLoginRecord(@RequestBody RefreshUserLoginVo refreshUserLoginVo);

}