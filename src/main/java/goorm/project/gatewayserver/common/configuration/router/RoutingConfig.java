package goorm.project.gatewayserver.common.configuration.router;

import goorm.project.gatewayserver.business.web.client.redis.RedisSessionService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpHeaders.*;

@Configuration
public class RoutingConfig {

    private final RedisSessionService redisSessionService;

    public RoutingConfig(RedisSessionService redisSessionService) {
        this.redisSessionService = redisSessionService;
    }

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
        List<String> memberIds = exchange.getRequest().getHeaders().get(SET_COOKIE);
        redisSessionService.isLoginUser(Long.parseLong(memberIds.get(0)));

        return chain.filter(exchange);
    }

}
