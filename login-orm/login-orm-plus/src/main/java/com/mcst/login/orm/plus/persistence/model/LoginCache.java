package com.mcst.login.orm.plus.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mcst.easyfk.core.annotation.PrimaryKey;
import com.mcst.easyfk.repository.persistence.annotation.EntityColumn;
import com.mcst.easyfk.service.mybatisplus.persistence.BaseMyBatisPlusSimpleEntity;
import com.mcst.login.api.dto.LoginCacheDto;
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
@TableName("login_cache")
@AutoMapper(target = LoginCacheDto.class)
public class LoginCache extends BaseMyBatisPlusSimpleEntity<LoginCache> {


    /**
     * ID
     */
    @PrimaryKey
    @TableId(type = IdType.ASSIGN_UUID)
    @EntityColumn(comment = "ID")
    private String loginCacheId;

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
     * 登录Token
     */
    @EntityColumn(comment = "登录Token")
    private String loginToken;

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