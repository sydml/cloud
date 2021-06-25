package com.sydml.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // 两种配置方式，一种在yml文件配置，一种通过启动bean配置
        return builder.routes()
                // basic proxy BAIDU-SERVER
//                .route(r -> r.path("/redis/**").filters(f -> f.stripPrefix(1)).uri("localhost:9001"))
//                .route(r -> r.path("/api-mybatis/**").filters(f -> f.stripPrefix(1)).uri("lb://MYBATIS"))
//                .route(r -> r.path("/redis/**").filters(f -> f.stripPrefix(1)).uri("localhost:9001"))
                .build();
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        // 限流key的key名明，一般代表某业务接口唯一业务含义的字段名配置在，网关传输分配给对方接口的业务码
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("LIMIT_KEY"));
    }

}
