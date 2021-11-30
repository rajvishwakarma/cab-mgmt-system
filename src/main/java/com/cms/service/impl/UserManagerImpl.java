package com.cms.service.impl;

import java.util.Collection;
import java.util.Map;

import com.cms.exception.UserAlreadyExistException;
import com.cms.models.User;
import com.cms.service.IStorageManager;
import com.cms.service.IUserManager;

public class UserManagerImpl implements IUserManager {
	
	private IStorageManager storageManager;
	private Map<String, User> userMap = null;
	
	public UserManagerImpl(IStorageManager storageManager) {
		this.storageManager = storageManager;
	}
	
	@Override
	public boolean createUser(User user) {
		userMap = this.storageManager.getUserData();
		if (userMap.containsKey(user.getMobile()))
			throw new UserAlreadyExistException();

		userMap.put(user.getMobile(), user);
		return true;
	}

	@Override
	public Collection<User> getAllUsers() {
		return this.storageManager.getUserData().values();
	}
}
