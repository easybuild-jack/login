package com.mcst.login.orm.flex.persistence.model;


import com.mcst.easyfk.core.annotation.PrimaryKey;
import com.mcst.easyfk.core.annotation.SingleUniqueField;
import com.mcst.easyfk.repository.persistence.annotation.EntityColumn;
import com.mcst.easyfk.service.flex.persistence.BaseFlexSimpleEntity;
import com.mcst.login.api.dto.LoginCacheDto;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 作者: eb-jack
 * 日期: 2026-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("login_cache")
@AutoMapper(target = LoginCacheDto.class)
public class LoginCache extends BaseFlexSimpleEntity<LoginCache> {


    /**
     * ID
     */
    @PrimaryKey
    @Id(keyType = KeyType.Generator, value = "uuid")
    @EntityColumn(comment = "ID")
    private String loginCacheId;

    /**
     * 登录Token
     */
    @SingleUniqueField(repetitionMsg = "登录Token不可重复")
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