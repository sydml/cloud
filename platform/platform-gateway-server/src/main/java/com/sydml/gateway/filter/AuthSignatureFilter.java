package com.sydml.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Liuym
 * @date 2019/5/14 0014
 */
//@Component
public class AuthSignatureFilter implements GlobalFilter, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSignatureFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("token");
        // String token = exchange.getRequest().getQueryParams().getFirst("authToken");
        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().toString();
            String addr = request.getRemoteAddress().getAddress().toString();
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            LOGGER.info("HOST:" + JSONObject.toJSONString(request.getURI()));
            LOGGER.info("PATH:" + path);
            LOGGER.info("ADDR:" + addr);
            LOGGER.info("PARAM:" + queryParams.toString());
        }));
    }

    @Override
    public int getOrder() {
        return -200;
    }
}

