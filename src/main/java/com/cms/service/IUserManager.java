package com.cms.service;

import java.util.Collection;

import com.cms.models.User;

import lombok.NonNull;

public interface IUserManager {
	
	public boolean createUser(@NonNull final User user);
	public Collection<User> getAllUsers();
}
