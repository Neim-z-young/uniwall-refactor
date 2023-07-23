package com.oyoungy.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class WallUserToken extends AbstractAuthenticationToken {

    private final Long id;
    private String role;

    /**
     * This factory method can be safely used by any code that wishes to create a
     * unauthenticated <code>WallUserToken</code>.
     * @param id
     * @param role
     * @return WallUserToken with false isAuthenticated() result
     *
     * @since 5.7
     */
    public static WallUserToken unauthenticated(Long id, String role) {
        return new WallUserToken(id, role);
    }

    /**
     * This factory method can be safely used by any code that wishes to create a
     * authenticated <code>WallUserToken</code>.
     * @param id
     * @param role
     * @return WallUserToken with true isAuthenticated() result
     *
     * @since 5.7
     */
    public static WallUserToken authenticated(Long id, String role,
                                              Collection<? extends GrantedAuthority> authorities) {
        return new WallUserToken(id, role, authorities);
    }

    private WallUserToken(Long id, String role) {
        super(null);
        this.id  = id;
        this.role = role;
        super.setAuthenticated(false);
    }

    private WallUserToken(Long id, String role, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id  = id;
        this.role = role;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return role;
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
