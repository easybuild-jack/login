package com.mcst.login.orm.hibernate.repository.impl;

import com.mcst.easyfk.service.hibernate.impl.BaseHibernateRepositoryImpl;
import com.mcst.login.api.dto.LoginCacheDto;
import com.mcst.login.orm.hibernate.persistence.mapper.LoginCacheMapper;
import com.mcst.login.orm.hibernate.persistence.model.LoginCache;
import com.mcst.login.orm.repository.ILoginCacheRepository;

/**
 * <p>
 * 登录缓存 Repository实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public class LoginCacheRepositoryImpl extends BaseHibernateRepositoryImpl<LoginCacheMapper, LoginCacheDto, LoginCache, String> implements ILoginCacheRepository {


}