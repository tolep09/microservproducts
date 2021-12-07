package com.gateway.app.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class ExampleGlobalFilter implements GlobalFilter, Ordered {
	private final Logger logger = LoggerFactory.getLogger(ExampleGlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Aqui van las tareas del filtro pre, o sea, antes de que se procese el request");
		//agregar un valor a los headers del request
		exchange.getRequest().mutate().headers(h -> h.add("token", "abcdefg1234567"));
		
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("Aqui van las tareas del filtro post, o sea, cunado ya se ha procesado el request");
			//extraer el header token del request y agregarlo a los headers del response
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(val -> {
				exchange.getResponse().getHeaders().add("token", val);
			});
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "blue").build());
		}));
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
