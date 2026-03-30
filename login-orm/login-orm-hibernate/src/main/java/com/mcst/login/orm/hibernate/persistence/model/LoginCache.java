package com.mcst.login.orm.hibernate.persistence.model;

import com.mcst.easyfk.core.annotation.PrimaryKey;
import com.mcst.easyfk.repository.persistence.annotation.EntityColumn;
import com.mcst.easyfk.service.hibernate.persistence.BaseHibernateEntity;
import com.mcst.easyfk.service.hibernate.persistence.BaseHibernateSimpleEntity;
import com.mcst.login.api.dto.LoginCacheDto;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

/**
 * 作者: eb-jack
 * 日期: 2026-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "login_cache")
@AutoMapper(target = LoginCacheDto.class)
public class LoginCache extends BaseHibernateSimpleEntity {


    /**
     * ID
     */
    @PrimaryKey
    @Id
    @UuidGenerator
    @EntityColumn(comment = "ID")
    @Column(name = "login_cache_id")
    private String loginCacheId;

    /**
     * 登录Token
     */
    @EntityColumn(comment = "登录Token")
    private String loginToken;

    /**
     * 用户ID
     */
    @EntityColumn(comment = "用户ID")
    private String userId;

    /**
     * 账号类型（platform，customer等）
     */
    @EntityColumn(comment = "账号类型（platform，customer等）")
    private String type;

    /**
     * 用户登录数据
     */
    @EntityColumn(comment = "用户登录数据")
    private String userData;

    /**
     * 用户权限
     */
    @EntityColumn(comment = "用户权限")
    private String userAuth;

    /**
     * 过期时间
     */
    @EntityColumn(comment = "过期时间")
    private LocalDateTime expireTime;
}