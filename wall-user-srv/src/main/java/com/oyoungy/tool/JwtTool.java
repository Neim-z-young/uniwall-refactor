package com.oyoungy.tool;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * access token:
 * Header jwt头部对象
 * {
 *     "alg":“HS256"
 *     "typ":"JWT"
 * }
 * Payload  jwt负载对象
 * {
 *     "id": userId(唯一且不为空)
 *     "type":
 *     "exp":
 *     "role": (用户角色)
 *     "nbf": not before time
 * }
 * Signature  jwt签名
 * {
 *
 * }
 *
 *
 * refresh token:
 * Header jwt头部对象
 * {
 *     "alg":“HS256"
 *     "typ":"JWT"
 * }
 * Payload  jwt负载对象
 * {
 *     "aud": userId(唯一且不为空)
 *     "type":
 *     "exp":
 *     "role": (用户角色)
 *     "nbf": not before time
 * }
 * Signature  jwt签名
 * {
 *
 * }
 *
 * Create by oyoungy on 2019/10/25
 */

@Slf4j
public class JwtTool {
    private static final String CLAIM_ID = "id";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_REFRESH = "refresh";
    private static final String TYPE_ACCESS = "access";


    @Data
    public static class JwtConf{
        private String secret;

        private Long expiration;

        private Long refreshExpiration;

        private String userRole;

        private String adminRole;

        private String tokenHeader;

        private String tokenHead;

        /**
         * 通常与链接中的spring的参数值保持一致，为ROLE_
         * 在spring security 的authenticationProvider中使用。
         * {@link org.springframework.security.authorization.AuthorityAuthorizationManager.ROLE_PREFIX}
         */
        private String rolePrefix;
    }

    @Setter
    @Getter
    private JwtConf jwtConf;

    /**
     * 生成token
     * 每次执行都会设置过期时间和CLAIM_CREATED_KEY的时间
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setNotBefore(new Date())
                .setExpiration(generateExpirationDate((String) claims.get(CLAIM_TYPE)))
                .signWith(SignatureAlgorithm.HS256, jwtConf.getSecret())
                .compact();
    }

    /**
     * 生成accessToken过期时间
     * @return
     */
    private Date generateExpirationDate(String type){
        Long sec = jwtConf.getExpiration();
        if(TYPE_REFRESH.equals(type)){
            sec = jwtConf.getRefreshExpiration();
        }
        return new Date(System.currentTimeMillis() + sec * 1000);
    }

    /**
     * 从token中获取负载Claims
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(jwtConf.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
           log.error("token 错误 " + e.getMessage());
        }
        return claims;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public Long getUserIdFromToken(String token){
        Long userId;
        Claims claims = getClaimsFromToken(token);
        try{
            userId = Long.parseLong((String) claims.get(CLAIM_ID));
        }catch (Exception e){
            userId = null;
        }
        return userId;
    }

    /**
     * 从token中获取角色
     * @param token
     * @return
     */
    public String getRoleFromToken(String token){
        String role;
        Claims claims = getClaimsFromToken(token);
        try{
            role = (String) claims.get(CLAIM_ROLE);
        }catch (Exception e){
            role = null;
        }
        return role;
    }

    /**
     * 从claims中获取用户名
     * @param claims
     * @return
     */
    private Long getUserIdFromToken(Claims claims){
        Long userId;
        try{
            userId = Long.parseLong((String) claims.get(CLAIM_ID));
        }catch (Exception e){
            userId = null;
        }
        return userId;
    }

    /**
     * 判断token是否过期
     * @param exp
     * @return
     */
    private boolean expired(Date exp){
        if(exp == null) return true;
        return exp.before(new Date());
    }

    /**
     * 验证token是否有效
     * @param token
     * @return
     */
    public boolean validateAccess(String token){
        Claims claims = getClaimsFromToken(token);
        Long userId = getUserIdFromToken(claims);
        return claims != null && userId != null
                && TYPE_ACCESS.equals(claims.get(CLAIM_TYPE))
                && !expired(claims.getExpiration());
    }

    public String generateAccessToken(Long id, String role) {
        return generateToken(createClaims(id, role, TYPE_ACCESS));
    }

    public String generateRefreshToken(Long id, String role) {
        return generateToken(createClaims(id, role, TYPE_REFRESH));
    }

    private Map<String, Object> createClaims(Long id, String role, String type){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_ID, String.valueOf(id));
        claims.put(CLAIM_TYPE, type);
        if(role!=null){
            if(role.equals(jwtConf.getUserRole())){
                claims.put(CLAIM_ROLE, jwtConf.getUserRole());
            }else if(role.equals(jwtConf.getAdminRole())){
                claims.put(CLAIM_ROLE, jwtConf.getAdminRole());
            }
        }
        return claims;
    }
    /**
     * 判断能否刷新token
     * @param token
     * @return
     */
    private boolean canRefreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims != null && TYPE_REFRESH.equals(claims.get(CLAIM_TYPE))
                && !expired(claims.getExpiration());
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        if (!canRefreshToken(token)){
            throw new IllegalArgumentException("token无效");
        }
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_TYPE, TYPE_ACCESS);
        claims.setExpiration(this.generateExpirationDate(TYPE_ACCESS));
        return generateToken(claims);
    }
}
