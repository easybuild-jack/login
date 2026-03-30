package com.mcst.login.orm.plus.persistence.model;

import com.mcst.easyfk.service.mybatisplus.persistence.BaseMyBatisPlusEntity;

import com.mcst.easyfk.core.annotation.PrimaryKey;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mcst.login.api.dto.LoginRecordDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import com.mcst.easyfk.repository.persistence.annotation.EntityColumn;
import java.time.LocalDateTime;

/**
* 作者: eb-jack
* 日期: 2026-03-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("login_record")
@AutoMapper(target = LoginRecordDto.class)
public class LoginRecord extends BaseMyBatisPlusEntity<LoginRecord> {


    /**
    * ID
    */
    @PrimaryKey
    @TableId(type = IdType.ASSIGN_UUID)
    @EntityColumn(comment = "ID")
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