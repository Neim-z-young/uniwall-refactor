package com.oyoungy.service;

import com.oyoungy.auth.JwtUserToken;
import com.oyoungy.ddd.application.dto.ValidateUserDTO;
import com.oyoungy.exceptions.WallBaseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    public ValidateUserDTO getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserToken jwtUserToken = null;
        if(authentication instanceof JwtUserToken){
            jwtUserToken = (JwtUserToken) authentication;
        }
        Optional<ValidateUserDTO> res = Optional.ofNullable(jwtUserToken).map(user-> {
            ValidateUserDTO validateUserDTO = new ValidateUserDTO();
            validateUserDTO.setId((Long) user.getPrincipal());
            validateUserDTO.setRole((String) user.getCredentials());
            return validateUserDTO;
        });
        return res.orElseThrow(()->new WallBaseException("用户未登录"));
    }
}
