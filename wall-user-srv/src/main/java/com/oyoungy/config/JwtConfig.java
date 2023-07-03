package com.oyoungy.config;

import com.oyoungy.tool.JwtTool;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtTool.JwtConf jwtConf(){
        return new JwtTool.JwtConf();
    }

    @Bean
    public JwtTool jwtTool(JwtTool.JwtConf jwtConf){
        JwtTool jwtTool = new JwtTool();
        jwtTool.setJwtConf(jwtConf);
        return jwtTool;
    }

}
