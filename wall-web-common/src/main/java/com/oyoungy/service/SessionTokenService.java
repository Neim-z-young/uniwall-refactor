package com.oyoungy.service;

import com.oyoungy.util.CommonUtils;

public interface SessionTokenService {
    boolean storeToken(String token);

    boolean removeToken(String token);

    boolean checkToken(String token);

    String getTokenHeader();

    default String generateToken(){
        return CommonUtils.genUUID();
    }
}
