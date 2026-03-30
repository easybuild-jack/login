package com.mcst.login.server.config;

import com.mcst.easyfk.authority.IRefreshUserLoginRecord;
import com.mcst.easyfk.authority.manager.UserDataManager;
import com.mcst.login.api.ILoginRecordApi;
import com.mcst.login.server.impl.LoginRecordApiServerImpl;
import com.mcst.login.server.impl.RefreshUserLoginServerImpl;
import com.mcst.login.server.impl.UserDataManagerServerImpl;
import com.mcst.login.server.service.ILoginCacheService;
import com.mcst.login.server.service.ILoginRecordService;
import com.mcst.login.server.service.IRefreshUserLoginService;
import com.mcst.login.server.service.IUserDataManagerService;
import com.mcst.login.server.service.impl.LoginCacheServiceImpl;
import com.mcst.login.server.service.impl.LoginRecordServiceImpl;
import com.mcst.login.server.service.impl.RefreshUserLoginServiceImpl;
import com.mcst.login.server.service.impl.UserDataManagerServiceImpl;
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
@EnableConfigurationProperties
public class LoginServerConfig {

    @Bean
    @ConditionalOnMissingBean
    public ILoginRecordApi loginRecordApi() {
        return new LoginRecordApiServerImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoginRecordService loginRecordService() {
        return new LoginRecordServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoginCacheService loginCacheService() {
        return new LoginCacheServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IRefreshUserLoginService refreshUserLoginService() {
        return new RefreshUserLoginServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IRefreshUserLoginRecord refreshUserLoginRecord() {
        return new RefreshUserLoginServerImpl();
    }


    @Bean
    @ConditionalOnProperty(name = "easyfk.config.login.cache-type", havingValue = "Database", matchIfMissing = true)
    public UserDataManager userDataManager() {
        return new UserDataManagerServerImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public IUserDataManagerService userDataManagerService() {
        return new UserDataManagerServiceImpl();
    }
}
