package com.oyoungy.auth;

import com.oyoungy.tool.JwtTool;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Setter
    private JwtTool jwtTool;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            Long id = (Long)authentication.getPrincipal();
            String role = (String)authentication.getCredentials();

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(jwtTool.getJwtConf().getRolePrefix() + role));

            return new JwtUserToken(
                    id,
                    role,
                    authorities);
        }catch (Exception ex){
            throw new AuthenticationServiceException(
                    MessageFormat.format("jwt认证异常{0}", ex.getMessage()), ex.getCause());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtUserToken.class.equals(authentication);
    }
}
