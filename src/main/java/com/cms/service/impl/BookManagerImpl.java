package com.cms.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cms.constants.CabStatus;
import com.cms.models.Booking;
import com.cms.models.Cab;
import com.cms.models.City;
import com.cms.models.User;
import com.cms.service.IBookingManager;
import com.cms.service.ICabManager;
import com.cms.service.IStorageManager;
import com.cms.utils.GenericUtils;

public class BookManagerImpl implements IBookingManager {

	private IStorageManager storageManager;
	private ICabManager cabManager;

	private Map<String, Booking> bookingMap = null;

	public BookManagerImpl(IStorageManager storageManager, ICabManager cabManager) {
		this.storageManager = storageManager;
		this.cabManager = cabManager;
	}

	@Override
	public Booking bookCab(City from, City to, User user) {
		List<Cab> cabs = (List<Cab>) cabManager.searchCabs(from);

		Booking booking = new Booking(UUID.randomUUID().toString(), from, to, cabs.get(0), user);

		bookingMap = this.storageManager.getBookingData();
		bookingMap.put(booking.getId(), booking);

		cabManager.updateCabStatus(cabs.get(0).getCab(), CabStatus.ON_TRIP);
		System.out.println("Cab " + cabs.get(0).getCab()+ " has been booked! for " + user.getFirstName() + " from " + from.getName() +" to "+ to.getName());;
		return booking;
	}

	@Override
	public Booking endTrip(Booking booking) {

		booking.setEndTS(System.currentTimeMillis());
		booking.setDuration(GenericUtils.getDuration(booking.getStartTS(), booking.getEndTS()));
		booking.setPrice(Math.random()*100);
		booking.setDistance(booking.getPrice().toString());
		booking.setStatus("TASK COMPLETED");
		bookingMap.put(booking.getId(), booking);
		
		cabManager.updateCabStatus(booking, CabStatus.IDLE);
		return booking;
	}

	
}
