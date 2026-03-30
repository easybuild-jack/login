package com.mcst.login.remote;

import com.mcst.easyfk.authority.vo.RefreshUserLoginVo;

/**
 * <p>
 * 登录记录 Remote接口类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public interface IRefreshUserLoginRemote {

    /**
     * 刷新用户登录记录过期时间
     */
    void refreshUserLoginRecord(RefreshUserLoginVo refreshUserLoginVo);

}