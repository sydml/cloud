package com.sydml.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Liuym
 * @date 2019/5/14 0014
 */
public class RequestFilter implements GatewayFilter, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().toString();
            String addr = request.getRemoteAddress().getAddress().toString();
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            LOGGER.info("PATH:" + path);
            LOGGER.info("ADDR:" + addr);
            LOGGER.info("PARAM:" + queryParams.toString());
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
