package com.cms.main;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.cms.constants.CabStatus;
import com.cms.constants.Role;
import com.cms.models.Booking;
import com.cms.models.Cab;
import com.cms.models.City;
import com.cms.models.User;
import com.cms.service.IBookingManager;
import com.cms.service.ICabManager;
import com.cms.service.ICityManager;
import com.cms.service.IStorageManager;
import com.cms.service.IUserManager;
import com.cms.service.impl.BookManagerImpl;
import com.cms.service.impl.CabManagerImpl;
import com.cms.service.impl.CityManagerImpl;
import com.cms.service.impl.StorageManagerImpl;
import com.cms.service.impl.UserManagerImpl;

public class CabMgmtSystem {
	
	private static IStorageManager storageManager = new StorageManagerImpl();
	private static IUserManager userManager = new UserManagerImpl(storageManager);
	private static ICityManager cityManager  = new CityManagerImpl(storageManager);
	private static ICabManager cabManager  = new CabManagerImpl(storageManager);
	private static IBookingManager bookingManager  = new BookManagerImpl(storageManager, cabManager);
	
	public static void main(String[] args) {
		
		User admin = new User(1, "Admin", "Admin", true, Role.ADMIN, "123");
		userManager.createUser(admin);
		
		User user1 = new User(2, "User 1", "User", true, Role.USER, "456");
		userManager.createUser(user1);
		
		User user2 = new User(3, "User 2", "User", true, Role.USER, "789");
		userManager.createUser(user2);
		
		System.out.println("Registered Users:");
		userManager.getAllUsers().stream()
			.forEach(user -> System.out.println(user.getId() + " - " + user.getFirstName() + " " + user.getLastName() + " Role: " + user.getRole()));
		System.out.println();
		
		cityManager.createCity(new City(1, "Mumbai", true));
		cityManager.createCity(new City(2, "Delhi", true));
		cityManager.createCity(new City(3, "Pune", true));
		cityManager.createCity(new City(4, "Surat", true));
		cityManager.createCity(new City(5, "Nashik", true));
		cityManager.createCity(new City(6, "Vadodara", true));
		cityManager.createCity(new City(7, "Jaipur", true));
		cityManager.createCity(new City(8, "Nagpur", true));
		cityManager.createCity(new City(9, "Hyderabad", true));
		cityManager.createCity(new City(10, "Bangalore", true));
		System.out.println("On Boarded Cities:");
		cityManager.getAllCities().stream().forEach(city -> System.out.println(city.getName()));
		System.out.println();
		
		cabManager.createCab(new Cab(1, "MH01A0001", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE));
		cabManager.createCab(new Cab(2, "MH01A0002", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Pune"), CabStatus.IDLE));
		cabManager.createCab(new Cab(3, "MH01A0003", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE));
		cabManager.createCab(new Cab(4, "MH01A0004", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Delhi"), CabStatus.IDLE));
		cabManager.createCab(new Cab(5, "MH01A0005", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE));
		cabManager.createCab(new Cab(6, "MH01A0006", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE));
		cabManager.createCab(new Cab(7, "MH01A0007", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Jaipur"), CabStatus.IDLE));
		cabManager.createCab(new Cab(8, "MH01A0008", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE));
		
		System.out.println("On Boarded Cabs:");
		cabManager.getAllCabs().stream()
			.forEach(cab -> System.out.println("Cab: " + cab.getCab() + ", Current City: " + cab.getCurrentCity().getName() + ", Status: " + cab.getStatus()));
		System.out.println();
		
		System.out.println("Cab: MH01A0008's current location: " +cabManager.getCab("MH01A0008").getCurrentCity().getName());
		System.out.println("Updating Cab MH01A0008 location from Mumbai to Hyderabad");
		cabManager.updateCabCurrentLocation("MH01A0008", cityManager.getCity("Hyderabad"));
		System.out.println("Cab: MH01A0008's updated current location: " + cabManager.getCab("MH01A0008").getCurrentCity().getName());
		
		System.out.println();
		System.out.println("Search Cabs...for User1 with city: Mumbai");
		System.out.println("Here are cab...for User1 with city: Mumbai and state IDLE");
		cabManager.searchCabs(cityManager.getCity("Mumbai")).stream()
			.forEach(cab -> System.out.println("Cab: " + cab.getCab() + " - " + cab.getLastIdleStatusTS()));
		
		bookingManager.bookCab(cityManager.getCity("Mumbai"), cityManager.getCity("Hyderabad"), user1);
		
		System.out.println();
		System.out.println("Current Cab Status:");
		cabManager.getAllCabs().stream()
			.forEach(cab -> System.out.println("Cab: " + cab.getCab() + ", Current City: " + cab.getCurrentCity().getName() + ", Status: " + cab.getStatus()));
		System.out.println();
		
		System.out.println();
		System.out.println("Search Cabs...for User2 with city: Mumbai");
		System.out.println("Here are cab...for User2 with city: Mumbai and state IDLE");
		cabManager.searchCabs(cityManager.getCity("Mumbai")).stream()
			.forEach(cab -> System.out.println("Cab: " + cab.getCab()));
		
        Booking booking = bookingManager.bookCab(cityManager.getCity("Mumbai"), cityManager.getCity("Jaipur"), user1);
		
		System.out.println();
		System.out.println("Current Cab Status:");
		cabManager.getAllCabs().stream()
			.forEach(cab -> System.out.println("Cab: " + cab.getCab() + ", Current City: " + cab.getCurrentCity().getName() + ", Status: " + cab.getStatus()));
		System.out.println();
		
		booking = bookingManager.endTrip(booking);
		System.out.println();
		System.out.println("Cab Trip Commpleted with details below -" );
		System.out.println("Booking ID: " + booking.getId());
		System.out.println("Cab: " + booking.getCab().getCab());
		System.out.println("From: " + booking.getFrom().getName() + ", To: " + booking.getTo().getName());
		System.out.println("Distance: " + booking.getDistance() + ", Price: " + booking.getPrice() + ", Duration: "+  booking.getDuration());
		System.out.println("Trip Status: " + booking.getStatus());
		
		
		System.out.println();
		System.out.println("Current Cab Status:");
		cabManager.getAllCabs().stream()
			.forEach(cab -> System.out.println("Cab: " + cab.getCab() + ", Current City: " + cab.getCurrentCity().getName() + ", Status: " + cab.getStatus()));
		System.out.println();
		
		System.out.println();
		System.out.println("History of Cab " + cabManager.getCab("MH01A0003").getCab());
		cabManager.getHistory(cabManager.getCab("MH01A0003")).stream()
			.forEach(ch -> System.out.println("Cab Status: " + ch.getStatus() + ", StartTS: " + ch.getStartTS() + ", EndTS: " + ch.getEndTS()));
		
		
		System.out.println();
		Date endTS = new Date();
		Date startTS = DateUtils.addDays(new Date(),-1);
		
		System.out.println("Time for which the Cab " + cabManager.getCab("MH01A0003").getCab() + " is Idle between " + startTS + " and " + endTS);
		
		System.out.println("Duration for which the cab was Idle: " + cabManager.getIdleTimeDuration(cabManager.getCab("MH01A0003"), startTS, endTS));
		
		
	}
}
