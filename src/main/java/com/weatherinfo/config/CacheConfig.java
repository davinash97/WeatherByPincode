package com.weatherinfo.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public CaffeineCacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("weatherByPincode");
		cacheManager.setCaffeine(Caffeine.newBuilder()
				.expireAfterWrite(1, TimeUnit.DAYS)
				.maximumSize(100));
		return cacheManager;
	}

}
