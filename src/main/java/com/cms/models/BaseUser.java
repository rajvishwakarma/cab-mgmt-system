package com.cms.models;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseUser {
	
	protected Integer id;
	protected String firstName;
	protected String lastName;
	protected String mobile;
	protected Boolean enabled = true;
	
	protected Date createdTS = new Date();
	protected Date updatedTS;
}
