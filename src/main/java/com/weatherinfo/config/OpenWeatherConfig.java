package com.weatherinfo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherConfig {

	@Value("${openweather.api.key}")
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}
}

