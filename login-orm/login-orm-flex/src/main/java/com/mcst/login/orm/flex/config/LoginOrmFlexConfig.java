package com.mcst.login.orm.flex.config;

import com.mcst.login.orm.flex.repository.impl.LoginCacheFlexRepositoryImpl;
import com.mcst.login.orm.flex.repository.impl.LoginRecordFlexRepositoryImpl;
import com.mcst.login.orm.repository.ILoginCacheRepository;
import com.mcst.login.orm.repository.ILoginRecordRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties
public class LoginOrmFlexConfig {

    @Bean
    public ILoginRecordRepository loginRecordRepository() {
        return new LoginRecordFlexRepositoryImpl();
    }


    @Bean
    public ILoginCacheRepository loginCacheRepository() {
        return new LoginCacheFlexRepositoryImpl();
    }
}
