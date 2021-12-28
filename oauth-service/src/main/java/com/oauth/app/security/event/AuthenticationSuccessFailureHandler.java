package com.oauth.app.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessFailureHandler implements AuthenticationEventPublisher {
	private final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessFailureHandler.class);

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String message = "Se ha logueado " + user.getUsername();
		logger.info(message);
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		logger.error("Error al loguearse " + exception.getMessage());
		
	}

}
