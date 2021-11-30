package com.cms.requests;

import com.cms.constants.CabStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CabUpdateRequest {
	
	private Long currentCity;
	private CabStatus status;
}
