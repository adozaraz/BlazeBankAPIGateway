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

    @Value("${backend.cardServiceUri}")
    private String cardServiceURI;

    @Value("${backend.paymentServiceUri}")
    private String paymentServiceURI;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        if (frontendUrl.endsWith("/")) {
            frontendUrl = frontendUrl.substring(0, frontendUrl.length()-1);
        }
        return builder.routes()
                .route("card-service", r -> r.path("/card/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.tokenRelay().removeRequestHeader("Cookie"))
                        .uri(cardServiceURI))
                .route("payment-service", r -> r.path("/payment/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.tokenRelay().removeRequestHeader("Cookie"))
                        .uri(paymentServiceURI))
                .route("frontend-service", r -> r.path("/**")
                        .uri(frontendUrl))
                .build();
    }

}
