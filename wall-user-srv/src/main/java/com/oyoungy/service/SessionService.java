package com.oyoungy.service;

import com.oyoungy.auth.WallUserToken;
import com.oyoungy.ddd.application.dto.ValidateUserDTO;
import com.oyoungy.exceptions.WallAuthFailException;
import com.oyoungy.exceptions.WallBaseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    public ValidateUserDTO getCurrentUser() throws WallAuthFailException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WallUserToken wallUserToken = null;
        if(authentication instanceof WallUserToken){
            wallUserToken = (WallUserToken) authentication;
        }
        Optional<ValidateUserDTO> res = Optional.ofNullable(wallUserToken).map(user-> {
            ValidateUserDTO validateUserDTO = new ValidateUserDTO();
            validateUserDTO.setId((Long) user.getPrincipal());
            validateUserDTO.setRole((String) user.getCredentials());
            return validateUserDTO;
        });
        return res.orElseThrow(()->new WallAuthFailException("用户未登录"));
    }
}
