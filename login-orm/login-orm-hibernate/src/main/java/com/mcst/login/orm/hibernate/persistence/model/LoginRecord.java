package com.mcst.login.orm.hibernate.persistence.model;

import com.mcst.easyfk.core.annotation.PrimaryKey;
import com.mcst.easyfk.repository.persistence.annotation.EntityColumn;
import com.mcst.easyfk.service.hibernate.persistence.BaseHibernateEntity;
import com.mcst.login.api.dto.LoginRecordDto;
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
@Table(name = "login_record")
@AutoMapper(target = LoginRecordDto.class)
public class LoginRecord extends BaseHibernateEntity {


    /**
     * ID
     */
    @PrimaryKey
    @Id
    @UuidGenerator
    @EntityColumn(comment = "ID")
    @Column(name = "login_record_id")
    private String loginRecordId;

    /**
     * 用户ID
     */
    @EntityColumn(comment = "用户ID")
    private String userId;

    /**
     * 手机号
     */
    @EntityColumn(comment = "手机号")
    private String phone;

    /**
     * 账号
     */
    @EntityColumn(comment = "账号")
    private String account;

    /**
     * 账号类型（platform，customer等）
     */
    @EntityColumn(comment = "账号类型（platform，customer等）")
    private String type;

    /**
     * 上次登录时间
     */
    @EntityColumn(comment = "上次登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 登录时间
     */
    @EntityColumn(comment = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 过期时间
     */
    @EntityColumn(comment = "过期时间")
    private LocalDateTime expireTime;

    /**
     * 登录IP
     */
    @EntityColumn(comment = "登录IP")
    private String loginIp;

    /**
     * 设备信息
     */
    @EntityColumn(comment = "设备信息")
    private String deviceInfo;

    /**
     * IP所属地
     */
    @EntityColumn(comment = "IP所属地")
    private String ipLocation;

    /**
     * 失败次数
     */
    @EntityColumn(comment = "失败次数")
    private Integer failCount;

    /**
     * 登录客户端类型（APP,WEB）
     */
    @EntityColumn(comment = "登录客户端类型（APP,WEB）")
    private String clientType;

    /**
     * 登录Token
     */
    @EntityColumn(comment = "登录Token")
    private String loginToken;
}