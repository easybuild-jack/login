package com.mcst.login.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.mcst.easyfk.core.dto.request.BasicParam;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

/**
* <p>
* 登录缓存数据参数对象
* </p>
*
* @author eb-jack
* @since 2026-03-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description="登录缓存数据参数对象")
@SuperBuilder
@NoArgsConstructor
public class LoginCacheParam extends BasicParam {

    /**
    * ID
    */
    @Schema(description= "ID")
    private String loginCacheId;

    /**
    * 登录Token
    */
    @Schema(description= "登录Token")
    private String loginToken;

    /**
     * 用户ID
     */
    @Schema(description= "用户ID")
    private String userId;

    /**
     * 账号类型（platform，customer等）
     */
    @Schema(description= "账号类型（platform，customer等）")
    private String type;

    /**
    * 用户登录数据
    */
    @Schema(description= "用户登录数据")
    private String userData;

    /**
    * 用户权限
    */
    @Schema(description= "用户权限")
    private String userAuth;

    /**
    * 过期时间
    */
    @Schema(description= "过期时间")
    private LocalDateTime expireTime;


}