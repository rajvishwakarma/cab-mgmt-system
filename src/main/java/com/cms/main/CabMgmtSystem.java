package com.cms.main;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.cms.constants.CabStatus;
import com.cms.constants.Role;
import com.cms.models.Admin;
import com.cms.models.BaseUser;
import com.cms.models.Booking;
import com.cms.models.Cab;
import com.cms.models.City;
import com.cms.models.Driver;
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
		
		BaseUser admin = new Admin(1, "Admin", "Admin", true, "123");
		userManager.createUser(admin);
		
		BaseUser user1 = new User(2, "User 1", "User", true, "456");
		userManager.createUser(user1);
		
		BaseUser user2 = new User(3, "User 2", "User", true, "789");
		userManager.createUser(user2);
		
		BaseUser driver1 = new Driver(3, "Driver 1", "User", true, "0001");
		userManager.createUser(driver1);
		BaseUser driver2 = new Driver(5, "Driver 2", "User", true, "0002");
		userManager.createUser(driver2);
		BaseUser driver3 = new Driver(6, "Driver 3", "User", true, "0003");
		userManager.createUser(driver3);
		BaseUser driver4 = new Driver(7, "Driver 4", "User", true, "0004");
		userManager.createUser(driver4);
		BaseUser driver5 = new Driver(8, "Driver 5", "User", true, "0005");
		userManager.createUser(driver5);
		BaseUser driver6 = new Driver(9, "Driver 6", "User", true, "0006");
		userManager.createUser(driver6);
		BaseUser driver7 = new Driver(10, "Driver 7", "User", true, "0007");
		userManager.createUser(driver7);
		BaseUser driver8 = new Driver(11, "Driver 8", "User", true, "0008");
		userManager.createUser(driver8);
		
		System.out.println("Registered Users:");
		userManager.getAllUsers().stream()
			.forEach(user -> {
				System.out.print(user.getId() + " | " + user.getFirstName() + ", " + user.getLastName() + " | "); 
				if(user instanceof User)
					System.out.print(" Role: " + Role.USER);
				else if(user instanceof Admin)
					System.out.print(" Role: " + Role.ADMIN);
				else if(user instanceof Driver)
					System.out.print(" Role: " + Role.DRIVER);
				System.out.println();
			});
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
		
		cabManager.createCab(new Cab(1, "MH01A0001", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE, (Driver)driver1));
		cabManager.createCab(new Cab(2, "MH01A0002", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Pune"), CabStatus.IDLE, (Driver)driver2));
		cabManager.createCab(new Cab(3, "MH01A0003", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE, (Driver)driver3));
		cabManager.createCab(new Cab(4, "MH01A0004", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Delhi"), CabStatus.IDLE, (Driver)driver4));
		cabManager.createCab(new Cab(5, "MH01A0005", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE, (Driver)driver5));
		cabManager.createCab(new Cab(6, "MH01A0006", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE, (Driver)driver6));
		cabManager.createCab(new Cab(7, "MH01A0007", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Jaipur"), CabStatus.IDLE, (Driver)driver7));
		cabManager.createCab(new Cab(8, "MH01A0008", "Swift", "Suzuki", "2021", "XYZ", cityManager.getCity("Mumbai"), CabStatus.IDLE, (Driver)driver8));
		
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
		
		bookingManager.bookCab(cityManager.getCity("Mumbai"), cityManager.getCity("Hyderabad"), (User)user1);
		
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
		
        Booking booking = bookingManager.bookCab(cityManager.getCity("Mumbai"), cityManager.getCity("Jaipur"), (User)user1);
		
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
		System.out.println("History of Cab " + cabManager.getCab("MH01A0003").getCab());
		cabManager.getHistory(cabManager.getCab("MH01A0003")).stream()
			.forEach(ch -> System.out.println("Cab Status: " + ch.getStatus() + ", StartTS: " + ch.getStartTS() + ", EndTS: " + ch.getEndTS()));
		
		
		System.out.println();
		Date endTS = new Date();
		Date startTS = DateUtils.addDays(new Date(),-1);
		
		System.out.println("Time Duration the Cab " + cabManager.getCab("MH01A0003").getCab() + " is Idle between " + startTS 
				+ " and " + endTS + " is: " + cabManager.getIdleTimeDuration(cabManager.getCab("MH01A0003"), startTS, endTS));
		
		System.out.println();
		bookingManager.getDemandCities();
		
	}
}
