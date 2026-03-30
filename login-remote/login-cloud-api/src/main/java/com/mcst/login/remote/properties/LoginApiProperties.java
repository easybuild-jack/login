package com.mcst.login.remote.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
*
* @author eb-jack
* @since 2026-03-30
*/
@ConfigurationProperties(prefix = "easyfk.config.remote.login")
@Data
public class LoginApiProperties {
    /**
    * Login模块的ServiceId配置
    */
    private String serviceId;
    /**
    * path 前缀地址，如：/remote 或/api 等，默认/remote
    */
    private String basePath;
}
