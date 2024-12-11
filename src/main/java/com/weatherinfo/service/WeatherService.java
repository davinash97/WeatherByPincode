package com.weatherinfo.service;

import com.weatherinfo.model.PincodeCoordinates;
import com.weatherinfo.model.WeatherData;

public interface WeatherService {

	WeatherData getWeatherByPincode(String pincode, String forDate);

	PincodeCoordinates getCoordinates(String pincode);

	Boolean saveCoordinates(String pincode, double lat, double lon);

	Boolean existsByPincode(String pincode);
}
