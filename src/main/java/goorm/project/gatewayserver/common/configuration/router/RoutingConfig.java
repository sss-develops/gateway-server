package goorm.project.gatewayserver.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Configuration
public class RoutingConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p

                        .path("/query-server/**")
                        .filters(f -> f.rewritePath("/query-server/(?<segment>.*)", "/${segment}")
                                .filters((exchange, chain) ->  gatewayFilter(exchange, chain))
                                .addResponseHeader("X-Response-Time", new Date().toString()))
                        .uri("lb://QUERY-SERVER")
                )
                .route(p -> p
                        .path("/authentication-server/**")
                        .filters(f -> f.rewritePath("/authentication-server/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", new Date().toString()))
                        .uri("lb://AUTHENTICATION-SERVER")
                )
                .route(p -> p
                        .path("/news-write-server/**")
                        .filters(f -> f.rewritePath("/news-write-server/(?<segment>.*)", "/${segment}")
                                .filters((exchange, chain) ->  gatewayFilter(exchange, chain))
                                .addResponseHeader("X-Response-Time", new Date().toString()))
                        .uri("lb://NEWS-WRITE-SERVER")

                )
                .build();
    }

    private Mono<Void> gatewayFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * todo: please 준우형~~
         */
        return chain.filter(exchange);
    }

}
