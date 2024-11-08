package ru.ssau.blazebankapigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Object body = exchange.getAttribute("cachedRequestBodyObject");
        System.out.println("in request filter");
        if(body instanceof Student) { //TODO: поправить на те, что будут использоваться в наших сервисах
            System.out.println("body:" + (Student) body);
        }
        else if (body instanceof Company) {
            System.out.println("body:" + (Company) body);
        }
        return chain.filter(exchange);
    }
}
