package com.mcst.login.remote.config;


import com.mcst.easyfk.authority.IRefreshUserLoginRecord;
import com.mcst.easyfk.authority.manager.UserDataManager;
import com.mcst.login.api.ILoginRecordApi;
import com.mcst.login.remote.impl.LoginRecordApiScImpl;
import com.mcst.login.remote.impl.RefreshUserLoginScImpl;
import com.mcst.login.remote.impl.UserDataManagerScImpl;
import com.mcst.login.remote.properties.LoginApiProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author eb-jack
 */
@AutoConfiguration
@EnableConfigurationProperties(LoginApiProperties.class)
public class LoginApiConfig {
    @Bean
    @ConditionalOnMissingBean
    public ILoginRecordApi loginRecordApi() {
        return new LoginRecordApiScImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public IRefreshUserLoginRecord refreshUserLoginRecord() {
        return new RefreshUserLoginScImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "easyfk.config.login.cache-type", havingValue = "Database", matchIfMissing = true)
    public UserDataManager userDataManager() {
        return new UserDataManagerScImpl();
    }
}
