package com.cms.service;

import java.util.List;
import java.util.Map;

import com.cms.models.BaseUser;
import com.cms.models.Booking;
import com.cms.models.Cab;
import com.cms.models.CabHistory;
import com.cms.models.City;

public interface IStorageManager {
	
	Map<String, BaseUser> getUserData();
	Map<String, Cab> getCabData();
	Map<String, City > getCityData();
	Map<String, Booking> getBookingData();
	Map<City, List<Cab>> getCabCityData();
	Map<Cab, List<CabHistory>> getCabHistory();
}
