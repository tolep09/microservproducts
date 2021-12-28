package com.oauth.app.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.users.commons.app.models.entity.User;

@FeignClient(name="users-service")
public interface IUserFeignClient {
	@GetMapping("/users/search/fbusername")
	public User findByUsername(@RequestParam String username);
}
