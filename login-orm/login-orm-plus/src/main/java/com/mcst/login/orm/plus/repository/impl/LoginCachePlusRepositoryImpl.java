package com.mcst.login.orm.plus.repository.impl;

import com.mcst.easyfk.service.mybatisplus.impl.BaseMyBatisRepositoryImpl;
import com.mcst.login.api.dto.LoginCacheDto;
import com.mcst.login.orm.plus.persistence.mapper.LoginCacheMapper;
import com.mcst.login.orm.plus.persistence.model.LoginCache;
import com.mcst.login.orm.repository.ILoginCacheRepository;

/**
 * <p>
 * 登录缓存 Repository实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public class LoginCachePlusRepositoryImpl extends BaseMyBatisRepositoryImpl<LoginCacheMapper, LoginCacheDto, LoginCache, String> implements ILoginCacheRepository {


}