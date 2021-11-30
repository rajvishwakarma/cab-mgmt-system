package com.cms.service;

import java.util.Collection;

import com.cms.models.City;

public interface ICityManager {
	
	public void createCity(City city);
	public City getCity(String cityName);
	public void updateCity(String cityName, boolean enabled);
	public Collection<City> getAllCities();
}
