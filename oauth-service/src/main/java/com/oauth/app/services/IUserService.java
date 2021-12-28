package com.oauth.app.services;

import com.users.commons.app.models.entity.User;

public interface IUserService {
	public abstract User findByUsername(String username);
}
