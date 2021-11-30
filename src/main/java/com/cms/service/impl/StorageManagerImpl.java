package com.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cms.models.Booking;
import com.cms.models.Cab;
import com.cms.models.CabHistory;
import com.cms.models.City;
import com.cms.models.User;
import com.cms.service.IStorageManager;

public class StorageManagerImpl implements IStorageManager {
	
	private Map<String, User> userData;
    private Map<String, Cab> cabData;
    private Map<String, City> cityData;
    private Map<String, Booking> bookingData;
    private Map<City, List<Cab>> cabCityData;
    private Map<Cab, List<CabHistory>> cabHistoryData;

    public StorageManagerImpl() {
        this.userData = new HashMap<>();
        this.cabData = new HashMap<>();
        this.cityData = new HashMap<>();
        this.bookingData = new HashMap<>();
        this.cabCityData = new HashMap<City, List<Cab>>();
        this.cabHistoryData = new HashMap<Cab, List<CabHistory>>();
    }

	@Override
	public Map<String, User> getUserData() {
		return userData;
	}

	@Override
	public Map<String, Cab> getCabData() {
		return cabData;
	}

	@Override
	public Map<String, City> getCityData() {
		return cityData;
	}

	@Override
	public Map<String, Booking> getBookingData() {
		return bookingData;
	}

	@Override
	public Map<City, List<Cab>> getCabCityData() {
		return cabCityData;
	}

	@Override
	public Map<Cab, List<CabHistory>> getCabHistory() {
		return cabHistoryData;
	}
    
}
