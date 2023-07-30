package com.oyoungy.auth;

import com.oyoungy.response.HttpCode;
import com.oyoungy.service.SessionTokenService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class WallUserSessionTokenFilter extends OncePerRequestFilter {
    @Setter
    SessionTokenService sessionTokenService;

    @Setter
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("WallSessionTokenFilter enter");
        String tokenHeader = sessionTokenService.getTokenHeader();
        log.debug("tokenHeader: " + tokenHeader);
        log.info("method: {}  url: {}", request.getMethod(), request.getRequestURI());
        String token = request.getHeader(tokenHeader);
        if (token != null && sessionTokenService.checkToken(token)){
            Authentication authentication = WallUserToken.unauthenticated(-1L, "session_user");
            try{
                //交给authenticationProvider代理类进行授权
                authentication = this.authenticationManager.authenticate(authentication);
            }catch (AuthenticationException e){
                log.error(e.getMessage(), e);
            }
            postProcessTokenAuthentication(authentication);
        }else{
//            response.sendError(HttpCode.UNAUTHORIZED.getCode(), HttpCode.UNAUTHORIZED.getMessage());
            log.info("WallSessionTokenFilter fail to authenticate");
//            return;
        }

        filterChain.doFilter(request, response);
        log.info("WallSessionTokenFilter out");
    }

    private void preProcessTokenAuthentication(){
        SecurityContextHolder.clearContext();
    }

    private void postProcessTokenAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
