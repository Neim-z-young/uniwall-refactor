package com.oyoungy.filter;

import com.oyoungy.response.ResultDTO;
import com.oyoungy.service.SessionTokenService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class AddSessionTokenGlobalFilter implements GlobalFilter, Ordered {
    @Setter
    private SessionTokenService sessionTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerWebExchange newExchange = exchange;
        ServerHttpRequest request = exchange.getRequest();
        String tokenHeader = sessionTokenService.getTokenHeader();
        String token = "";
        String method = request.getMethodValue();
        String urlPath = request.getURI().getRawPath();
        if(!request.getHeaders().containsKey(tokenHeader)){
            ServerHttpRequest.Builder builder = request.mutate();
            token = sessionTokenService.generateToken();
            if(!tryStoreToken(token)){
                ServerHttpResponse response = exchange.getResponse();
                var buffer = response.bufferFactory().wrap("会话请求异常".getBytes());
                response.setStatusCode(HttpStatus.FORBIDDEN);
                log.error("request url:{} method:{} failed with session token:{}", urlPath, method, token);
                return response.writeWith(Mono.just(buffer));
            }
            builder.header(tokenHeader, token);
            newExchange = exchange.mutate().request(builder.build()).build();
        }else{
            token = request.getHeaders().getFirst(tokenHeader);
        }

        log.info("request url:{} method:{} entered with session token:{}", urlPath, method, token);
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return 100;
    }

    private boolean tryStoreToken(String token){
        boolean result = false;
        try{
            CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(
                    () -> sessionTokenService.storeToken(token));
            result = f.get();
        } catch (InterruptedException e) {
            log.error("AddSessionTokenGlobalFilter try store token Interrupted");
        } catch (ExecutionException e) {
            log.error("AddSessionTokenGlobalFilter try store token Failed");
        }
        return result;
    }
}
