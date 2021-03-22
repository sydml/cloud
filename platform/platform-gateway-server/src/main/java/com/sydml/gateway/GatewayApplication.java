package com.sydml.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // basic proxy BAIDU-SERVER
                .route(r -> r.path("/api-authorization/**").filters(f -> f.stripPrefix(1)).uri("lb://AUTHORIZATION"))
                .route(r -> r.path("/api-mybatis/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://MYBATIS"))
                .build();
    }

}
