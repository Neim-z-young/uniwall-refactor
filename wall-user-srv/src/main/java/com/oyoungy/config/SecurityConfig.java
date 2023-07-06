package com.oyoungy.config;

import com.oyoungy.auth.JwtAuthenticationProvider;
import com.oyoungy.auth.JwtAuthenticationTokenFilter;
import com.oyoungy.tool.JwtTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.annotation.WebFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(JwtTool jwtTool){
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
        jwtAuthenticationProvider.setJwtTool(jwtTool);
        return jwtAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, JwtTool jwtTool) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET, "/user/{\\d+}").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/*/login",
                        "/*/refresh",
                        "/user/register").permitAll()
                .antMatchers("/user/**").hasRole(jwtTool.getJwtConf().getUserRole())
                .anyRequest().authenticated()
        ;
        http.addFilterBefore(jwtAuthenticationTokenFilter(authenticationManager, jwtTool), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存（不使用session，故基本用不上缓存）
        http.headers().cacheControl();

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, JwtTool jwtTool) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider(jwtTool));
        return authenticationManagerBuilder.build();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, JwtTool jwtTool) throws Exception{
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationTokenFilter.setJwtTool(jwtTool);
        return jwtAuthenticationTokenFilter;
    }
}
