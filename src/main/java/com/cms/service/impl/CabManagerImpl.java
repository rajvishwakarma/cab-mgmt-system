package com.cms.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.cms.constants.CabStatus;
import com.cms.exception.CabAlreadyExistsException;
import com.cms.exception.CabNotFoundException;
import com.cms.models.Booking;
import com.cms.models.Cab;
import com.cms.models.CabHistory;
import com.cms.models.City;
import com.cms.service.ICabManager;
import com.cms.service.IStorageManager;
import com.cms.utils.GenericUtils;

import lombok.NonNull;

public class CabManagerImpl implements ICabManager{

	private IStorageManager storageManager;
	private Map<String, Cab> cabMap = null;
	private Map<City, List<Cab>> cabCityMap = null;
	private Map<Cab, List<CabHistory>> cabHistoryMap = null;

	public CabManagerImpl(IStorageManager storageManager) {
		this.storageManager = storageManager;
	}

	@Override
	public boolean createCab(@NonNull final Cab cab) {
		cabMap = this.storageManager.getCabData();
		cabCityMap = this.storageManager.getCabCityData();
		if (cabMap.containsKey(cab.getCab())) 
			throw new CabAlreadyExistsException();

		cabMap.put(cab.getCab(), cab);
		addCityCabMapping(cab.getCurrentCity(), cab);
		return true;
	}

	@Override
	public Cab getCab(@NonNull final String cab) {
		cabMap = this.storageManager.getCabData();
		if (!cabMap.containsKey(cab)) 
			throw new CabNotFoundException();

		return cabMap.get(cab);
	}

	@Override
	public boolean updateCabCurrentLocation(@NonNull final String cab, @NonNull final City currentCity) {
		cabMap = this.storageManager.getCabData();
		if (!cabMap.containsKey(cab)) {
			throw new CabNotFoundException();
		}

		Cab existing  = cabMap.get(cab);
		cabCityMap.get(existing.getCurrentCity()).removeIf(c -> c.getCab().equals(existing.getCab()));
		cabMap.get(cab).setCurrentCity(currentCity);

		addCityCabMapping(currentCity, cabMap.get(cab));
		return true;
	}

	@Override
	public boolean updateCabStatus(@NonNull final String cab, @NonNull final CabStatus status) {
		return updateCabStatus(cab, status, null);
	}

	public boolean updateCabStatus(@NonNull final String cab, @NonNull final CabStatus status, Booking booking) {
		cabMap = this.storageManager.getCabData();
		if (!cabMap.containsKey(cab)) {
			throw new CabNotFoundException();
		}
		Cab existing  = cabMap.get(cab);
		cabCityMap.get(existing.getCurrentCity()).removeIf(c -> c.getCab().equals(existing.getCab()));

		Date lastIdleStatusTS = null;
		if(existing.getStatus().equals(CabStatus.ON_TRIP) && status.equals(CabStatus.IDLE)) {

			cabMap.get(cab).setLastIdleStatusTS(new Date());
		}  else if(existing.getStatus().equals(CabStatus.IDLE) && status.equals(CabStatus.ON_TRIP)) {
			lastIdleStatusTS = cabMap.get(cab).getLastIdleStatusTS();
			cabMap.get(cab).setLastIdleStatusTS(null);
		}
		cabMap.get(cab).setStatus(status);

		addCityCabMapping(existing.getCurrentCity(), cabMap.get(cab));

		cabHistoryMap = this.storageManager.getCabHistory();
		CabHistory cabHistory = null;
		if(null == booking)
			cabHistory = new CabHistory(CabStatus.IDLE, lastIdleStatusTS, new Date());
		else
			cabHistory = new CabHistory(CabStatus.ON_TRIP, booking.getCreatedTS(), new Date());
		addCabHistory(cabMap.get(cab), cabHistory);

		return true;
	}



	@Override
	public boolean updateCabAvailability(@NonNull final String cab, boolean enabled) {
		cabMap = this.storageManager.getCabData();
		if (!cabMap.containsKey(cab)) 
			throw new CabNotFoundException();

		cabMap.get(cab).setEnabled(enabled);
		return true;
	}

	@Override
	public Collection<Cab> getAllCabs() {
		return this.storageManager.getCabData().values();
	}

	@Override
	public Collection<Cab> searchCabs(City city) {
		
		List<Cab> cabs = cabCityMap.get(city);
		if(CollectionUtils.isEmpty(cabs))
			return Collections.EMPTY_LIST;
		
		return cabs.stream()
				.filter(cab -> cab.getStatus().equals(CabStatus.IDLE) && cab.getEnabled())
				.sorted(Comparator.comparing(Cab::getLastIdleStatusTS))
				.collect(Collectors.toList());
	}

	public void addCityCabMapping(City city, Cab cab) {
		if(cabCityMap.containsKey(city))
			cabCityMap.get(city).add(cabMap.get(cab.getCab()));
		else 
			cabCityMap.put(city, new ArrayList<Cab>() {{ add(cabMap.get(cab.getCab())); }});
	}

	public void addCabHistory(Cab cab, CabHistory cabHistory) {
		if(cabHistoryMap.containsKey(cab))
			cabHistoryMap.get(cab).add(cabHistory);
		else 
			cabHistoryMap.put(cab, new ArrayList<CabHistory>() {{ add(cabHistory); }});
	}

	@Override
	public boolean updateCabStatus(Booking booking, CabStatus status) {
		return updateCabStatus(booking.getCab().getCab(), status, booking);
	}

	@Override
	public List<CabHistory> getHistory(Cab cab) {
		return this.storageManager.getCabHistory().get(cab);
	}

	@Override
	public String getIdleTimeDuration(Cab cab, Date startTS, Date endTS) {
		List<CabHistory> cabHistroy = this.storageManager.getCabHistory().get(cab);
		Date start = startTS;
		Date end = endTS;

		long diff = 0;
		for (CabHistory cabHistory : cabHistroy) {
			if(!CabStatus.IDLE.equals(cabHistory.getStatus()))
				continue;
			
			if(cabHistory.getStartTS().after(start))
				start = cabHistory.getStartTS();

			if(cabHistory.getEndTS().before(end))
				end = cabHistory.getEndTS();

			diff = end.getTime() - start.getTime();
		}
		return GenericUtils.getDuration(diff);
	}
}
