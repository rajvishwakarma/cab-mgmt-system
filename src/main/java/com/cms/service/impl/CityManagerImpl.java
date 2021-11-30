package com.cms.service.impl;

import java.util.Collection;
import java.util.Map;

import com.cms.exception.CityAlreadyExistException;
import com.cms.exception.CityNotFoundException;
import com.cms.models.City;
import com.cms.service.ICityManager;
import com.cms.service.IStorageManager;

import lombok.NonNull;

public class CityManagerImpl implements ICityManager {

	private IStorageManager storageManager;
	private Map<String, City> cityMap = null;
	
	public CityManagerImpl(IStorageManager storageManager) {
		this.storageManager = storageManager;
	}

	@Override
	public void createCity(@NonNull final City city) {
		cityMap = this.storageManager.getCityData();
		if (cityMap.containsKey(city.getName())) 
			throw new CityAlreadyExistException();

		cityMap.put(city.getName(), city);
	}

	@Override
	public City getCity(@NonNull final String cityName) {
		cityMap = this.storageManager.getCityData();
		if (!cityMap.containsKey(cityName))
			throw new CityNotFoundException();

		return cityMap.get(cityName);
	}

	@Override
	public void updateCity(@NonNull final String cityName, boolean enabled) {
		cityMap = this.storageManager.getCityData();
		if (!cityMap.containsKey(cityName)) 
			throw new CityNotFoundException();

		cityMap.get(cityName).setEnabled(enabled);
	}

	@Override
	public Collection<City> getAllCities() {
		return this.storageManager.getCityData().values();
	}

}
