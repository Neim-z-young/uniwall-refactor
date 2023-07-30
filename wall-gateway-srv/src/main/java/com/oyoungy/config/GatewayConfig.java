package com.oyoungy.config;

import com.oyoungy.filter.AddSessionTokenGlobalFilter;
import com.oyoungy.filter.RemoveSessionTokenGlobalFilter;
import com.oyoungy.service.SessionTokenService;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter addSessionTokenGlobalFilter(SessionTokenService sessionTokenService){
        AddSessionTokenGlobalFilter sessionTokenGlobalFilter = new AddSessionTokenGlobalFilter();
        sessionTokenGlobalFilter.setSessionTokenService(sessionTokenService);
        return sessionTokenGlobalFilter;
    }

    @Bean
    public GlobalFilter removeSessionTokenGlobalFilter(SessionTokenService sessionTokenService){
        RemoveSessionTokenGlobalFilter sessionTokenGlobalFilter = new RemoveSessionTokenGlobalFilter();
        sessionTokenGlobalFilter.setSessionTokenService(sessionTokenService);
        return sessionTokenGlobalFilter;
    }
}
