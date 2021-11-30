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
public class Booking {

	private String id;
	private City from;
	private City to;
	private Cab cab;
	private User user;
	private Double price;
	private long startTS;
	private long endTS;
	private String duration;
	private String distance;
	private String status;
	
	private Date createdTS = new Date();
	private Date updatedTS;
	
	public Booking(String id, City from, City to, Cab cab, User user) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.cab = cab;
		this.user = user;
		this.startTS = System.currentTimeMillis();
	}
	
	
}
