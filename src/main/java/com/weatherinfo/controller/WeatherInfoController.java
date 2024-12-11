package com.weatherinfo.controller;

import com.weatherinfo.model.PincodeCoordinates;
import com.weatherinfo.model.Request;
import com.weatherinfo.model.WeatherData;
import org.springframework.web.bind.annotation.*;

import com.weatherinfo.model.Response;
import com.weatherinfo.service.WeatherService;


@RestController
@RequestMapping("/api")
public class WeatherInfoController {

	private final WeatherService weatherService;

	public WeatherInfoController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping("/weather")
	 public Response<WeatherData> getWeather(@RequestBody(required = false) Request body,
											 @RequestParam(required = false, value = "pincode") String pincode,
											 @RequestParam(required = false, value = "for_date")String forDate) {

		if(pincode.length() != 6) Error.notFound();

	 	if(body != null) {
	 		if(pincode == null) pincode = body.getPincode();
	 		if(forDate == null) forDate = body.getFor_date();
	 	}

	 	return new Response<>(200, weatherService.getWeatherByPincode(pincode, forDate));
	 }

	 @GetMapping
	 public Response<PincodeCoordinates> getCoords(@RequestParam("pincode") String pincode) {
		return new Response<>(200, weatherService.getCoordinates(pincode));
	 }

	 @PostMapping
	 public Response<PincodeCoordinates> setCoords(@RequestParam("pincode") String pincode,
							   @RequestParam("latitude") double latitude,
							   @RequestParam("longitude") double longitude) {
		 weatherService.saveCoordinates(pincode, latitude, longitude);
		return new Response<PincodeCoordinates>(200, weatherService.getCoordinates(pincode));
	 }

	static class Error {
		static Response<?> notFound() {
			return new Response<>(404, "not found");
		}
	}
}
