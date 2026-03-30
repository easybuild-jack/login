package com.mcst.login.remote.config;


import com.mcst.easyfk.authority.IRefreshUserLoginRecord;
import com.mcst.easyfk.authority.manager.UserDataManager;
import com.mcst.login.api.ILoginRecordApi;
import com.mcst.login.remote.impl.LoginRecordApiDubboImpl;
import com.mcst.login.remote.impl.RefreshUserLoginDubboImpl;
import com.mcst.login.remote.impl.UserDataManagerDubboImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author eb-jack
 */
@AutoConfiguration
public class LoginApiConfig {
    @Bean
    @ConditionalOnMissingBean
    public ILoginRecordApi loginRecordApi() {
        return new LoginRecordApiDubboImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public IRefreshUserLoginRecord refreshUserLoginRecord() {
        return new RefreshUserLoginDubboImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "easyfk.config.login.cache-type", havingValue = "Database", matchIfMissing = true)
    public UserDataManager userDataManager() {
        return new UserDataManagerDubboImpl();
    }

}
