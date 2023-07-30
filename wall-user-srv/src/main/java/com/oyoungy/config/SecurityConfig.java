package com.oyoungy.config;

import com.oyoungy.auth.WallUserAuthenticationProvider;
import com.oyoungy.auth.WallUserSessionTokenFilter;
import com.oyoungy.service.SessionTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.annotation.WebFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http,
                                                      AuthenticationManager authenticationManager,
                                                      SessionTokenService sessionTokenService) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET, "/user/{\\d+}").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/*/login",
                        "/*/refresh",
                        "/user/register").permitAll()
                .anyRequest().authenticated()
        ;
        http.addFilterBefore(wallUserSessionTokenFilter(authenticationManager, sessionTokenService),
                UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存（不使用session，故基本用不上缓存）
        http.headers().cacheControl();

        return http.build();
    }

    @Bean
    public WallUserAuthenticationProvider wallUserAuthenticationProvider(){
        return new WallUserAuthenticationProvider();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(wallUserAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public WallUserSessionTokenFilter wallUserSessionTokenFilter(
            AuthenticationManager authenticationManager,
            SessionTokenService sessionTokenService) throws Exception{
        WallUserSessionTokenFilter wallUserSessionTokenFilter = new WallUserSessionTokenFilter();
        wallUserSessionTokenFilter.setAuthenticationManager(authenticationManager);
        wallUserSessionTokenFilter.setSessionTokenService(sessionTokenService);
        return wallUserSessionTokenFilter;
    }
}
