package com.shopme.admin.shippingrate;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.ShippingRates;


public interface ShippingRateRepository extends PagingAndSortingRepository<ShippingRates, Integer> {
	
	@Query("SELECT sr FROM ShippingRates sr WHERE sr.country.id = ?1 AND sr.state = ?2 ")
	public ShippingRates findByCountryAndState(Integer countryId, String state);
	
	@Query("UPDATE ShippingRates sr SET sr.codSupported = ?2 WHERE sr.id = ?1")
	@Modifying
	public void updateCODSuported(Integer id, boolean enabled);
	
	@Query("SELECT sr FROM ShippingRates sr WHERE sr.country.name LIKE %?1% OR sr.state LIKE %?1%")
	public Page<ShippingRates> findAll(String keyword,  Pageable pageable);
	
	public Long countById(Integer id);

}
