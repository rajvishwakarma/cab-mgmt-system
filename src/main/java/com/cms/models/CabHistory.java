package com.cms.models;

import java.util.Date;
import java.util.UUID;

import com.cms.constants.CabStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CabHistory {
	
	private String id;
	private CabStatus status;
	private Date startTS;
	private Date endTS;
	private Date createdTS = new Date();
	
	public CabHistory(CabStatus status, Date startTS, Date endTS) {
		this.id = UUID.randomUUID().toString();
		this.status = status;
		this.startTS = startTS;
		this.endTS = endTS;
	}
	
	
}
