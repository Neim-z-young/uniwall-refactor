package com.oyoungy.service.Impl;

import com.oyoungy.service.SessionTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnMissingBean(RestTemplate.class)
public class LocalSessionTokenServiceImpl implements SessionTokenService {
    @Value("${wall.session.token-header:wallTokenHeader}")
    private String tokenHeader;

    @Override
    public boolean storeToken(String token) {
        return true;
    }

    @Override
    public boolean removeToken(String token) {
        return true;
    }

    @Override
    public boolean checkToken(String token) {
        return true;
    }

    @Override
    public String getTokenHeader() {
        return tokenHeader;
    }
}
