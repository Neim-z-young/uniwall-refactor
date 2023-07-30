package com.oyoungy.filter;

import com.oyoungy.response.HttpCode;
import com.oyoungy.service.SessionTokenService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class WallSessionTokenFilter extends OncePerRequestFilter {
    @Setter
    SessionTokenService sessionTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("WallSessionTokenFilter enter");
        String tokenHeader = sessionTokenService.getTokenHeader();
        log.debug("tokenHeader: " + tokenHeader);
        log.info("method: {}  url: {}", request.getMethod(), request.getRequestURI());
        String token = request.getHeader(tokenHeader);
        if (token == null || !sessionTokenService.checkToken(token)){
            response.sendError(HttpCode.UNAUTHORIZED.getCode(), HttpCode.UNAUTHORIZED.getMessage());
            log.info("WallSessionTokenFilter fail");
            return;
        }
        filterChain.doFilter(request, response);
        log.info("WallSessionTokenFilter out");
    }
}
