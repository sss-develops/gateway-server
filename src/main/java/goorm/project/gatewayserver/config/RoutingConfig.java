package goorm.project.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

        @Configuration
        public class RoutingConfig {

            @Bean
            public RouteLocator routeLocator(RouteLocatorBuilder builder) {
                return builder.routes()
                        .route(p -> p
                                .path("/query-server/**")
                                .filters(f -> f.rewritePath("/query-server/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", new Date().toString()))
                                .uri("lb://QUERYSERVER")
                        )
                        .route(p -> p
                                .path("/authentication-server/**")
                                .filters(f -> f.rewritePath("/authentication-server/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", new Date().toString()))
                                .uri("lb://AUTHENTICATIONSERVER")
                ).build();
    }
}
