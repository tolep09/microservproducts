package com.oauth.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oauth.app.clients.IUserFeignClient;
import com.users.commons.app.models.entity.User;

import feign.FeignException;

@Service
public class UserService implements IUserService, UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private IUserFeignClient feignClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			com.users.commons.app.models.entity.User myuser = feignClient.findByUsername(username);
			
			List<GrantedAuthority> authorities = myuser.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.collect(Collectors.toList());
			
			logger.info("Se ha acreditado: " + username);
			
			
			return new org.springframework.security.core.userdetails.User(myuser.getUsername(), myuser.getPassword(), 
				myuser.getEnable(), true, true, true, authorities);
			
		} catch (FeignException e) {
			logger.error("El usuario " + username + " no existe.");
			throw new UsernameNotFoundException("El usuario " + username + " no existe.");
		}
	}

	@Override
	public User findByUsername(String username) {
		return feignClient.findByUsername(username);
	}

}
