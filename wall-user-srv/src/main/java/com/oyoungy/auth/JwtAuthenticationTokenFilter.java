package com.oyoungy.auth;


import com.oyoungy.response.HttpCode;
import com.oyoungy.tool.JwtTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * JWT登录授权过滤器
 * token过滤器应当作用所有请求上(oncePerRequest)
 *
 * Create by oyoungy on 2019/10/25
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Setter
    private JwtTool jwtTool;

    //TODO cache

    @Setter
    @Getter
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationTokenFilter() {
    }

    private void preProcessTokenAuthentication(){
        SecurityContextHolder.clearContext();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = ((HttpServletRequest)request).getHeader(jwtTool.getJwtConf().getTokenHeader());
        String tokenHead = jwtTool.getJwtConf().getTokenHead();
        Authentication authentication = null;
        log.debug("AuthHeader: " + authHeader);
        log.info("jwt filter enter");
        log.info("method: {}", request.getMethod());
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            preProcessTokenAuthentication();
            String token = authHeader.substring(tokenHead.length() + 1); //authHeader = tokenHead + " " + token
            if (jwtTool.validateAccess(token)) {
                Long userId = jwtTool.getUserIdFromToken(token);
                String role = jwtTool.getRoleFromToken(token);
                log.debug("TOKEN: " + token + " userId: " + userId + " role: " + role);
                authentication = JwtUserToken.unauthenticated(userId, role);

                try{
                    //交给authenticationProvider代理类进行授权
                    authentication = this.getAuthenticationManager().authenticate(authentication);
                    postProcessTokenAuthentication(authentication);
                }catch (AuthenticationException e){
                    log.error(e.getMessage(), e);
                }
            }else{
                ((HttpServletResponse)response).sendError(HttpCode.UNAUTHORIZED.getCode(), HttpCode.UNAUTHORIZED.getMessage());
                log.info("jwt filter fail");
                return;
            }
        }
        filterChain.doFilter(request, response);
        log.info("jwt filter out");
    }

    private void postProcessTokenAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
