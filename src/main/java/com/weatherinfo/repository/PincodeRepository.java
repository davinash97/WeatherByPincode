package com.weatherinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weatherinfo.model.PincodeCoordinates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PincodeRepository extends JpaRepository<PincodeCoordinates, Long> {
	@Query("SELECT p FROM pincode_coords p WHERE p.pincode = :pincode")
	PincodeCoordinates findByPincode(@Param("pincode") String pincode);

	@Query("SELECT COUNT(p) > 0 FROM pincode_coords p WHERE p.pincode = :pincode")
	Boolean existsByPincode(@Param("pincode") String pincode);
}
