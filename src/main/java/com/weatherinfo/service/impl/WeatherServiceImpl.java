package com.weatherinfo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.weatherinfo.config.OpenWeatherConfig;
import com.weatherinfo.exception.ResourceNotFoundException;
import com.weatherinfo.exception.WeatherServiceException;
import com.weatherinfo.model.PincodeCoordinates;
import com.weatherinfo.model.WeatherData;
import com.weatherinfo.repository.PincodeRepository;
import com.weatherinfo.service.WeatherService;

@Service
@PropertySource("file:${user.dir}/src/main/resources/config.env")
public class WeatherServiceImpl implements WeatherService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	private final RestTemplate restTemplate;

	@Autowired
	private PincodeRepository repository;

	@Value("${openweather.api.key}")
	private String apiKey;

	public WeatherServiceImpl(OpenWeatherConfig openWeatherConfig, RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Boolean saveCoordinates(String pincode, double lat, double lon) {
		PincodeCoordinates coordinates = new PincodeCoordinates(pincode, lat, lon);
		repository.save(coordinates);
		return repository.existsByPincode(pincode);
	}

	@Override
	@Cacheable(value = "coords")
	public PincodeCoordinates getCoordinates(String pincode) {
		PincodeCoordinates coords = repository.findByPincode(pincode);
		return existsByPincode(pincode) ? coords: null;
	}

	@Override
	public Boolean existsByPincode(String pincode) {
		return repository.existsByPincode(pincode);
	}

	@Override
	@Cacheable(value = "weatherByPincode")
	public WeatherData getWeatherByPincode(String pincode, String forDate) {
		try {
			String url = String.format(
					"http://api.openweathermap.org/data/2.5/weather?zip=%s,IN&appid=%s&units=metric",
					pincode, apiKey
			);

			WeatherData weatherResponse = restTemplate.getForObject(url, WeatherData.class);

			if (weatherResponse != null) {
				weatherResponse.setPincode(pincode);
				weatherResponse.setFor_date(forDate);
				saveCoordinates(pincode, weatherResponse.getCoordinates().getLatitude(), weatherResponse.getCoordinates().getLongitude());
			} else if (weatherResponse == null || weatherResponse.getCoordinates() == null) {
				var error = new ResourceNotFoundException("Weather data not found for pincode: " + pincode);
				logger.error("Error fetching weather data for pincode {}: {}", pincode, error);
				throw error;
			}

			return weatherResponse;
		} catch (ResourceNotFoundException | RestClientException e) {
			logger.error("Error fetching weather data for pincode {}: {}", pincode, e.getMessage());
			throw new WeatherServiceException("Failed to fetch weather data: " + e.getMessage());
		}
	}
}
