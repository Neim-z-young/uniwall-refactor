package com.oyoungy.service.Impl;

import com.oyoungy.service.SessionTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty("spring.redis.host")
public class RedisCacheSessionTokenServiceImpl implements SessionTokenService {
    @Value("${wall.session.token-header:wallTokenHeader}")
    private String tokenHeader;

    @Override
    public boolean storeToken(String token) {
        return false;
    }

    @Override
    public boolean removeToken(String token) {
        return false;
    }

    @Override
    public boolean checkToken(String token) {
        return false;
    }

    @Override
    public String getTokenHeader() {
        return tokenHeader;
    }
}
