package com.cms.service;

import com.cms.models.Booking;
import com.cms.models.City;
import com.cms.models.User;

public interface IBookingManager {
	
	public Booking bookCab(City from, City to, User user);

	public Booking endTrip(Booking booking);
}
