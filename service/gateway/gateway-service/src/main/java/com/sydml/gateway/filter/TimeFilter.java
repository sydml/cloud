package com.sydml.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Liuym
 * @date 2019/5/14 0014
 */
public class TimeFilter implements GatewayFilter, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);
    private static final String COUNT_Start_TIME = "countStartTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(COUNT_Start_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(COUNT_Start_TIME);
            Long endTime = (System.currentTimeMillis() - startTime);
            if (startTime != null) {
                LOGGER.info(exchange.getRequest().getURI().toString() + ": " + endTime + "ms");
            }
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
