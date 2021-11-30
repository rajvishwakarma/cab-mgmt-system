package com.cms.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.cms.constants.CabStatus;
import com.cms.models.Booking;
import com.cms.models.Cab;
import com.cms.models.CabHistory;
import com.cms.models.City;

public interface ICabManager {
	
	public boolean createCab(Cab cab);
	public Cab getCab(String cab);
	public boolean updateCabCurrentLocation(String cab, City currentCity);
	public boolean updateCabStatus(String cab, CabStatus status);
	public boolean updateCabAvailability(String cab, boolean enabled);
	public Collection<Cab> getAllCabs();
	public Collection<Cab> searchCabs(City city);
	public boolean updateCabStatus(Booking booking, CabStatus idle);
	public List<CabHistory> getHistory(Cab cab);
	public String getIdleTimeDuration(Cab cab, Date startTS, Date endTS);
}
