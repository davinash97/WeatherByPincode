package com.weatherinfo.model;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;

@ResponseBody
public class WeatherData {

	public String pincode;
	public String for_date;

	@JsonProperty("coord")
	public Coordinates coordinates;

	@JsonProperty("main")
	public Weather weather;

	@JsonProperty("weather")
	public List<Description> description;

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public void setFor_date(String for_date) {
		this.for_date = for_date;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public Weather getWeather() {
		return weather;
	}

	public static class Weather {

		@JsonProperty("temp")
		private double temperature;

		private double humidity;

		public double getTemperature() {
			return temperature;
		}

		public double getHumidity() {
			return humidity;
		}
	}

	public static class Coordinates {

		@JsonProperty("lon")
		private double longitude;

		@JsonProperty("lat")
		private double latitude;

		public double getLongitude() {
			return longitude;
		}

		public double getLatitude() {
			return latitude;
		}
	}

	public static class Description {

		@JsonProperty("main")
		private String main;
		
		@JsonProperty("description")
		private String description;

		public String getMain() {
			return main;
		}

		public String getDescription() {
			return description;
		}
	}
}
