package com.oyoungy.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class JwtToolTest {

     static JwtTool jwtTool;
     static JwtTool jwtToolExpired;

    @BeforeAll
    public static void init(){
        JwtTool.JwtConf jwtConf = new JwtTool.JwtConf();
        jwtConf.setAdminRole("ADMIN");
        jwtConf.setUserRole("USER");
        jwtConf.setExpiration((long)300);
        jwtConf.setRefreshExpiration((long)600);
        jwtConf.setRolePrefix("ROLE_");
        jwtConf.setSecret("dG1w");
        jwtConf.setTokenHead("Bearer");
        jwtConf.setTokenHeader("Authentication");
        jwtTool = new JwtTool();
        jwtTool.setJwtConf(jwtConf);

        JwtTool.JwtConf jwtConf1 = new JwtTool.JwtConf();
        jwtConf1.setAdminRole("ADMIN");
        jwtConf1.setUserRole("USER");
        jwtConf1.setExpiration((long)-10);
        jwtConf1.setRefreshExpiration((long)-10);
        jwtConf1.setRolePrefix("ROLE_");
        jwtConf1.setSecret("dG1w");
        jwtConf1.setTokenHead("Bearer");
        jwtConf1.setTokenHeader("Authentication");
        jwtToolExpired = new JwtTool();
        jwtToolExpired.setJwtConf(jwtConf1);
    }

    @Test
    public void generateAccessToken() {
        String refreshToken = jwtTool.generateRefreshToken((long)1, jwtTool.getJwtConf().getUserRole());
        String token = jwtTool.refreshToken(refreshToken);
        Assertions.assertTrue(jwtTool.validateAccess(token));
        Assertions.assertFalse(jwtTool.validateAccess(refreshToken));
        Assertions.assertEquals(Long.valueOf(1), jwtTool.getUserIdFromToken(token));
        Assertions.assertEquals(jwtTool.getJwtConf().getUserRole(), jwtTool.getRoleFromToken(token));
        Assertions.assertThrows(IllegalArgumentException.class, () -> jwtToolExpired.refreshToken(token));
    }

    @Test
    public void tokenExpired() {
        String token = jwtToolExpired.generateAccessToken((long)1, jwtTool.getJwtConf().getUserRole());
        Assertions.assertFalse(jwtToolExpired.validateAccess(token));

        String refreshToken = jwtToolExpired.generateRefreshToken((long)1, jwtTool.getJwtConf().getUserRole());
        Assertions.assertThrows(IllegalArgumentException.class, () -> jwtToolExpired.refreshToken(refreshToken));
    }
    //TODO jwt secret  base64编码
}