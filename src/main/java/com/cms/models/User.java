package com.cms.models;

import com.cms.constants.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends BaseUser {

	private Role role = Role.USER;
	
	public User(Integer id, String firstName, String lastName, Boolean enabled, String mobile) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;
		this.mobile = mobile;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		return true;
	}
}
