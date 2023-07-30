package com.oyoungy.filter;


import com.oyoungy.ddd.application.query.PermissionQuery;
import com.oyoungy.response.ResultDTO;
import com.oyoungy.tool.JwtTool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.layout.PatternMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class AuthCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthCheckGatewayFilterFactory.Config> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTool jwtTool;

    private PathPatternParser pathPatternParser = new PathPatternParser();

    /**
     * Method key.
     */
    public static final String METHOD_KEY = "methodRegex";

    /**
     * Except Paths key.
     */
    public static final String PATH_KEY = "exceptPaths";


    public AuthCheckGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(METHOD_KEY, PATH_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String method = request.getMethodValue();
            String urlPath = request.getURI().getRawPath();
            String token = extractToken(request);
            if(!checkSupport(config, method, urlPath)){
                log.info("AuthCheckFilter don't check url:{} method:{}", urlPath, method);
                return chain.filter(exchange);
            }

            PermissionQuery query = new PermissionQuery();
            query.setMethod(method);
            query.setToken(token);
            query.setUrl(urlPath);
            HttpEntity<PermissionQuery> entity = new HttpEntity<>(query, request.getHeaders());
            String userService = "http://user-srv/auth";
            ResponseEntity<ResultDTO> result = null;
            try{
                CompletableFuture<ResponseEntity<ResultDTO>> f = CompletableFuture.supplyAsync(
                        () -> restTemplate.exchange(userService,
                        HttpMethod.POST,
                        entity,
                        ResultDTO.class));
                result = f.get();
            } catch (InterruptedException e) {
                log.error("AuthCheckFilter request to {} with url:{} method:{} Interrupted",
                        userService, urlPath, method);
            } catch (ExecutionException e) {
                log.error("AuthCheckFilter request to {} with url:{} method:{} Failed",
                        userService, urlPath, method);
            }

            ResultDTO resultDTO = Optional.ofNullable(result).map(ResponseEntity::getBody).
                    orElse(ResultDTO.failed("权限校验失败"));
            if(resultDTO.isSuccess()){
                log.info("AuthCheckFilter checked url:{} method:{}", urlPath, method);
                return chain.filter(exchange);
            }else{
                log.error("AuthCheckFilter denied url:{} method:{} reason:{}",
                        urlPath, method, resultDTO.getMessage());
                ServerHttpResponse response = exchange.getResponse();
                var buffer = response.bufferFactory().wrap(resultDTO.getMessage().getBytes());
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.writeWith(Mono.just(buffer));
            }
        };
    }

    private String extractToken(ServerHttpRequest request){
        String authHeader = request.getHeaders().getFirst(jwtTool.getJwtConf().getTokenHeader());
        String tokenHead = jwtTool.getJwtConf().getTokenHead();
        log.debug("AuthHeader: " + authHeader);
        log.info("auth filter enter");
        log.info("method: {}", request.getMethod());
        String token = null;
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            token = authHeader.substring(tokenHead.length() + 1); //authHeader = tokenHead + " " + token
        }
        return token;
    }

    private boolean checkSupport(Config config, String method, String urlPath){
        if(!method.matches(config.methodRegex)){
            return false;
        }
        final ArrayList<PathPattern> pathPatterns = new ArrayList<>();
        synchronized (this.pathPatternParser) {
            pathPatternParser.setMatchOptionalTrailingSeparator(true);
            config.getExceptPaths().forEach(pattern -> {
                PathPattern pathPattern = this.pathPatternParser.parse(pattern);
                pathPatterns.add(pathPattern);
            });
        }
        PathContainer path = PathContainer.parsePath(urlPath);
        PathPattern match = null;
        for (int i = 0; i < pathPatterns.size(); i++) {
            PathPattern pathPattern = pathPatterns.get(i);
            if (pathPattern.matches(path)) {
                match = pathPattern;
                break;
            }
        }
        return match == null;
    }

    @Data
    public static class Config {
        /**
         * 待匹配的http方法的正则表达式
         */
        private String methodRegex;
        /**
         * 不需要认证过滤的特殊路径
         * 格式为{@link org.springframework.web.util.pattern.PathPattern}。
         */
        private List<String> exceptPaths;
    }

}