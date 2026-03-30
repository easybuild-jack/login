package com.mcst.login.orm.hibernate.config;

import com.mcst.login.orm.hibernate.repository.impl.LoginCacheRepositoryImpl;
import com.mcst.login.orm.hibernate.repository.impl.LoginRecordRepositoryImpl;
import com.mcst.login.orm.repository.ILoginCacheRepository;
import com.mcst.login.orm.repository.ILoginRecordRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties
public class LoginOrmConfig {

    @Bean
    public ILoginRecordRepository loginRecordRepository() {
        return new LoginRecordRepositoryImpl();
    }


    @Bean
    public ILoginCacheRepository loginCacheRepository() {
        return new LoginCacheRepositoryImpl();
    }
}
