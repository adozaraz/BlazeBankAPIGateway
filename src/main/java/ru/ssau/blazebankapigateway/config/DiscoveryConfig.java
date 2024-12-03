package ru.ssau.blazebankapigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
public class DiscoveryConfig {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        if (frontendUrl.endsWith("/")) {
            frontendUrl = frontendUrl.substring(0, frontendUrl.length()-1);
        }
        return builder.routes()
                .route("music-service", r -> r.path("/musics/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.tokenRelay().removeRequestHeader("Cookie"))
                        .uri("lb://MUSIC-SERVICE/musics"))
                .route("frontend-service", r -> r.path("/frontend/**")
                        .uri(frontendUrl))
                .route("frontend-service", r -> r.path("/static/**")
                        .uri("%s/static".formatted(frontendUrl)))
                .build();
    }

}
