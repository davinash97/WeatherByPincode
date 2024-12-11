package com.weatherinfo.exception;

public class WeatherServiceException extends RuntimeException {
	public WeatherServiceException(String message) {
		super(message);
	}
}
