package com.cms.models;

import java.util.Date;

import com.cms.constants.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

	private Integer id;
	private String firstName;
	private String lastName;
	private String mobile;
	private Boolean enabled = true;
	
	private Role role;
	
	private Date createdTS = new Date();
	private Date updatedTS;
	
	public User(Integer id, String firstName, String lastName, Boolean enabled, Role role, String mobile) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;
		this.role = role;
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
