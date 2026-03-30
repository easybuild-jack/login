package com.mcst.login.orm.plus.config;

import com.mcst.login.orm.plus.repository.impl.LoginCachePlusRepositoryImpl;
import com.mcst.login.orm.plus.repository.impl.LoginRecordPlusRepositoryImpl;
import com.mcst.login.orm.repository.ILoginCacheRepository;
import com.mcst.login.orm.repository.ILoginRecordRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties
public class LoginOrmPlusConfig {

    @Bean
    public ILoginRecordRepository loginRecordRepository() {
        return new LoginRecordPlusRepositoryImpl();
    }


    @Bean
    public ILoginCacheRepository loginCacheRepository() {
        return new LoginCachePlusRepositoryImpl();
    }
}
