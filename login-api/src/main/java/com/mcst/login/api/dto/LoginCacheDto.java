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
* 登录缓存
* </p>
*
* @author eb-jack
* @since 2026-03-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description="登录缓存数据对象")
@SuperBuilder
@NoArgsConstructor
public class LoginCacheDto extends BaseDto {


    /**
    * ID
    */
    @PrimaryKey
    @Schema(description= "ID")
    private String loginCacheId;

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
    * 登录Token
    */
    @Schema(description= "登录Token")
    private String loginToken;

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
