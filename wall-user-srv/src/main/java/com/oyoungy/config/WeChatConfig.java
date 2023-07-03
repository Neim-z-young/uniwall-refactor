package com.oyoungy.config;

import com.oyoungy.util.WeChatTool;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatConfig {
    @Bean
    @ConfigurationProperties(prefix = "we-chat")
    public WeChatTool.WeChatConf weChatConf(){
        return new WeChatTool.WeChatConf();
    }

    @Bean
    public WeChatTool weChatTool(WeChatTool.WeChatConf chatConf){
        WeChatTool weChatTool = new WeChatTool();
        weChatTool.setWeChatConf(chatConf);
        return weChatTool;
    }
}
