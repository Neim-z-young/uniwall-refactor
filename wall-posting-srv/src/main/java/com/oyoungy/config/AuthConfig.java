package com.oyoungy.config;

import com.oyoungy.filter.WallSessionTokenFilter;
import com.oyoungy.service.SessionTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AuthConfig {
    @Bean
    @Profile({"test", "prod"})
    public WallSessionTokenFilter wallSessionTokenFilter(SessionTokenService sessionTokenService){
        WallSessionTokenFilter wallSessionTokenFilter = new WallSessionTokenFilter();
        wallSessionTokenFilter.setSessionTokenService(sessionTokenService);
        return wallSessionTokenFilter;
    }
}
