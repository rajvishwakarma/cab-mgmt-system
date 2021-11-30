package com.cms.service;

import java.util.Collection;

import com.cms.models.BaseUser;

import lombok.NonNull;

public interface IUserManager {
	
	public boolean createUser(@NonNull final BaseUser user);
	public Collection<BaseUser> getAllUsers();
}
