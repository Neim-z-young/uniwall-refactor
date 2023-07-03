package com.oyoungy.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class JwtUserToken extends AbstractAuthenticationToken {

    private final Long id;
    private String credentials;

    public JwtUserToken(Long id, String credentials) {
        super(null);
        this.id  = id;
        this.credentials = credentials;
        super.setAuthenticated(false);
    }

    public JwtUserToken(Long id, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id  = id;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }

    /**
     * An implementation refer to {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
     * @param isAuthenticated
     * @throws IllegalArgumentException
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }
}
