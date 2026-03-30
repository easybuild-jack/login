package com.mcst.login.remote.provider.config;

import com.mcst.login.remote.ILoginRecordRemote;
import com.mcst.login.remote.IRefreshUserLoginRemote;
import com.mcst.login.remote.IUserDataManagerRemote;
import com.mcst.login.remote.provider.impl.LoginRecordRemoteImpl;
import com.mcst.login.remote.provider.impl.RefreshUserLoginRemoteImpl;
import com.mcst.login.remote.provider.impl.UserDataManagerRemoteImpl;

import org.apache.dubbo.config.ServiceConfig;
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
    public ServiceConfig<ILoginRecordRemote> loginRecordRemote(LoginRecordRemoteImpl loginRecordRemoteImpl) {
        ServiceConfig<ILoginRecordRemote> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ILoginRecordRemote.class);
        serviceConfig.setRef(loginRecordRemoteImpl);
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceConfig<IUserDataManagerRemote> userDataManagerRemote(
            UserDataManagerRemoteImpl userDataManagerRemoteImpl) {
        ServiceConfig<IUserDataManagerRemote> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(IUserDataManagerRemote.class);
        serviceConfig.setRef(userDataManagerRemoteImpl);
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceConfig<IRefreshUserLoginRemote> refreshUserLoginRemote(
            RefreshUserLoginRemoteImpl refreshUserLoginRemoteImpl) {
        ServiceConfig<IRefreshUserLoginRemote> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(IRefreshUserLoginRemote.class);
        serviceConfig.setRef(refreshUserLoginRemoteImpl);
        serviceConfig.export();
        return serviceConfig;
    }

}
