package com.oyoungy.mock;

import com.oyoungy.auth.JwtUserToken;
import com.oyoungy.tool.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithJwtTokenUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Autowired
    private JwtTool jwtTool;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(jwtTool.getJwtConf().getRolePrefix() + jwtTool.getJwtConf().getUserRole()));

        Authentication auth =
                JwtUserToken.authenticated(1L, "password", authorities);
        context.setAuthentication(auth);
        return context;
    }
}
