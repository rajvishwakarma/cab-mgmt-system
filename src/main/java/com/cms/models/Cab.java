package com.cms.models;

import java.util.Date;

import com.cms.constants.CabStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cab {
	
	private Integer id;
	
	private String cab;
	private String model;
	private String make;
	private String year;
	private String owner;
	private Boolean enabled = true;
	
	private Date createdTS = new Date();
	private Date updatedTS;
	
    private City currentCity;
	
	private CabStatus status;
	private Date lastIdleStatusTS = new Date();

	public Cab(Integer id, String cab, String model, String make, String year, String owner, City currentCity,
			CabStatus status) {
		this.id = id;
		this.cab = cab;
		this.model = model;
		this.make = make;
		this.year = year;
		this.owner = owner;
		this.currentCity = currentCity;
		this.status = status;
	}
	
}
