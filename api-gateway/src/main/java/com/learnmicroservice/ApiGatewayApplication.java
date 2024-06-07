package com.learnmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/eazybank/account-service/**")
                        .filters(f -> f.rewritePath("/eazybank/account-service/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountCircuitBreaker")
                                        .setFallbackUri("foward:/contactSupport"))
                        )
                        .uri("lb://ACCOUNT-SERVICE")
                )
                .route(p -> p
                        .path("/eazybank/loan-service/**")
                        .filters(f -> f.rewritePath("/eazybank/loan-service/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://LOAN-SERVICE")
                )
                .route(p -> p
                        .path("/eazybank/card-service/**")
                        .filters(f -> f.rewritePath("/eazybank/card-service/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://CARD-SERVICE")
                )
                .build();
    }
}
