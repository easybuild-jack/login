package com.mcst.login.remote.provider.config;

import com.mcst.login.remote.ILoginRecordRemote;
import com.mcst.login.remote.IRefreshUserLoginRemote;
import com.mcst.login.remote.IUserDataManagerRemote;
import com.mcst.login.remote.provider.impl.LoginRecordRemoteImpl;
import com.mcst.login.remote.provider.impl.RefreshUserLoginRemoteImpl;
import com.mcst.login.remote.provider.impl.UserDataManagerRemoteImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author eb-jack
 */
@AutoConfiguration
public class LoginProviderConfig {

    @Bean
    @ConditionalOnMissingBean
    public ILoginRecordRemote loginRecordRemote() {
        return new LoginRecordRemoteImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public IRefreshUserLoginRemote refreshUserLoginRemote() {
        return new RefreshUserLoginRemoteImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IUserDataManagerRemote userDataManagerRemote() {
        return new UserDataManagerRemoteImpl();
    }

}
