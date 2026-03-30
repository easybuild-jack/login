package com.mcst.login.orm.flex.repository.impl;

import com.mcst.easyfk.service.flex.impl.BaseFlexRepositoryImpl;
import com.mcst.login.api.dto.LoginCacheDto;
import com.mcst.login.orm.flex.persistence.mapper.LoginCacheMapper;
import com.mcst.login.orm.flex.persistence.model.LoginCache;
import com.mcst.login.orm.repository.ILoginCacheRepository;

/**
 * <p>
 * 登录缓存 Repository实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public class LoginCacheFlexRepositoryImpl extends BaseFlexRepositoryImpl<LoginCacheMapper, LoginCacheDto, LoginCache, String> implements ILoginCacheRepository {


}