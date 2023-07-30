package com.oyoungy.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class WallUserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            Long id = (Long)authentication.getPrincipal();
            String role = (String)authentication.getCredentials();

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            return WallUserToken.authenticated(
                    id,
                    role,
                    authorities);
        }catch (Exception ex){
            throw new AuthenticationServiceException(
                    MessageFormat.format("认证异常{0}", ex.getMessage()), ex.getCause());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WallUserToken.class.equals(authentication);
    }
}
