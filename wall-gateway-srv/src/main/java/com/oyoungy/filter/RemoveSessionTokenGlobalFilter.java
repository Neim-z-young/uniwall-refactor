package com.oyoungy.filter;

import com.oyoungy.service.SessionTokenService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class RemoveSessionTokenGlobalFilter implements GlobalFilter, Ordered {

    @Setter
    private SessionTokenService sessionTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> removeToken(exchange)));
    }

    @Override
    public int getOrder() {
        return 100;
    }

    private boolean tryRemoveToken(String token){
        boolean result = false;
        try{
            CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(
                    () -> sessionTokenService.removeToken(token));
            result = f.get();
        } catch (InterruptedException e) {
            log.error("AddSessionTokenGlobalFilter try store token Interrupted");
        } catch (ExecutionException e) {
            log.error("AddSessionTokenGlobalFilter try store token Failed");
        }
        return result;
    }

    private void removeToken(ServerWebExchange exchange){
        ServerHttpRequest request = exchange.getRequest();
        String tokenHeader = sessionTokenService.getTokenHeader();
        String token = "";
        String method = request.getMethodValue();
        String urlPath = request.getURI().getRawPath();
        if(request.getHeaders().containsKey(tokenHeader)){
            token = request.getHeaders().getFirst(tokenHeader);
            if(!tryRemoveToken(token)){
                log.error("response for url:{} method:{} failed to remove session token:{}", urlPath, method, token);
            }else{
                log.info("response for url:{} method:{} succeed to remove session token:{}", urlPath, method, token);
            }
        }
    }
}
