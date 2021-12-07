package com.gateway.app.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuration> {
	private final Logger logger = LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);
	
	public ExampleGatewayFilterFactory() {
		super(Configuration.class);
	}

	@Override
	public GatewayFilter apply(Configuration config) {
		return new OrderedGatewayFilter((exchange, chain) -> {
			logger.info("Aqui van las sentencias del filtro pre " +  config.message);
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				//agregar una cookie a la respuesta
				Optional.ofNullable(config.cookieValue).ifPresent(val -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, val).build());
				});
				logger.info("Aqui van las sentencias del filtro post " + config.message);				
			}));
		}, 2);
	}
	
	
	
	@Override
	public String name() {
		return "FilterAddCookie";
	}



	public static class Configuration {
		private String message;
		private String cookieName;
		private String cookieValue;
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCookieName() {
			return cookieName;
		}
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}
		public String getCookieValue() {
			return cookieValue;
		}
		public void setCookieValue(String cookieValue) {
			this.cookieValue = cookieValue;
		}
	}

}
