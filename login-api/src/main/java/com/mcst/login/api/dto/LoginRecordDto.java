package com.mcst.login.api.dto;
import com.mcst.easyfk.core.annotation.PrimaryKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Accessors;
import com.mcst.easyfk.core.dto.BaseDto;
import java.time.LocalDateTime;

/**
* <p>
* 登录记录
* </p>
*
* @author eb-jack
* @since 2026-03-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description="登录记录数据对象")
@SuperBuilder
@NoArgsConstructor
public class LoginRecordDto extends BaseDto {


    /**
    * ID
    */
    @PrimaryKey
    @Schema(description= "ID")
    private String loginRecordId;

    /**
    * 用户ID
    */
    @Schema(description= "用户ID")
    private String userId;

    /**
    * 手机号
    */
    @Schema(description= "手机号")
    private String phone;

    /**
    * 账号
    */
    @Schema(description= "账号")
    private String account;

    /**
    * 账号类型（platform，customer等）
    */
    @Schema(description= "账号类型（platform，customer等）")
    private String type;

    /**
    * 上次登录时间
    */
    @Schema(description= "上次登录时间")
    private LocalDateTime lastLoginTime;

    /**
    * 登录时间
    */
    @Schema(description= "登录时间")
    private LocalDateTime loginTime;

    /**
    * 过期时间
    */
    @Schema(description= "过期时间")
    private LocalDateTime expireTime;

    /**
    * 登录IP
    */
    @Schema(description= "登录IP")
    private String loginIp;

    /**
    * 设备信息
    */
    @Schema(description= "设备信息")
    private String deviceInfo;

    /**
    * IP所属地
    */
    @Schema(description= "IP所属地")
    private String ipLocation;

    /**
    * 失败次数
    */
    @Schema(description= "失败次数")
    private Integer failCount;

    /**
    * 登录客户端类型（APP,WEB）
    */
    @Schema(description= "登录客户端类型（APP,WEB）")
    private String clientType;

    /**
    * 登录Token
    */
    @Schema(description= "登录Token")
    private String loginToken;


}
