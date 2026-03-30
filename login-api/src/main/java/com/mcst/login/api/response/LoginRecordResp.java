package com.mcst.login.api.response;

import com.mcst.login.api.dto.LoginRecordDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* <p>
* 登录记录数据响应对象
* </p>
*
* @author eb-jack
* @since 2026-03-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description="登录记录数据响应对象")
@SuperBuilder
@NoArgsConstructor
public class LoginRecordResp extends LoginRecordDto {


}
