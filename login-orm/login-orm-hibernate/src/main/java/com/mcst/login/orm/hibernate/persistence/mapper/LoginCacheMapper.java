package com.mcst.login.orm.hibernate.persistence.mapper;

import com.mcst.login.orm.hibernate.persistence.model.LoginCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <p>
 * 登录缓存 Mapper 接口
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public interface LoginCacheMapper extends JpaRepository<LoginCache, String>, JpaSpecificationExecutor<LoginCache> {

}

