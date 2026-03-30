package com.mcst.login.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.mcst.login.api.param.LoginCacheParam;

/**
* <p>
* 登录缓存数据请求对象
* </p>
*
* @author eb-jack
* @since 2026-03-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description="登录缓存数据请求对象")
@SuperBuilder
@NoArgsConstructor
public class LoginCacheReq extends LoginCacheParam {


}
