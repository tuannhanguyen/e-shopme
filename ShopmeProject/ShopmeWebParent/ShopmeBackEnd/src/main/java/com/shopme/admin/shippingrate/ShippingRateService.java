package com.shopme.admin.shippingrate;


import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRates;


@Service
@Transactional
public class ShippingRateService {

	@Autowired ShippingRateRepository repository;
	@Autowired CountryRepository countryRepository;
	public static final int RATES_PER_PAGE = 10;
	
	public List<ShippingRates> findAll(){
		return (List<ShippingRates>) repository.findAll();
	}
	
	public Page<ShippingRates> listByPage( int pageNum, String keyword, String sortDir, String sortField) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, RATES_PER_PAGE, sort);
		
		if(keyword != null) {
			return repository.findAll(keyword, pageable);
		}
		
		return repository.findAll(pageable);
	}
	
	public List<Country> listAllCountries(){
		return countryRepository.findAllByOrderByNameAsc();
	}
	
	public void save(ShippingRates rateInForm) throws ShippingRateAlreadyExistsException {
		ShippingRates rateInDb = repository.findByCountryAndState(rateInForm.getCountry().getId(), rateInForm.getState());
		boolean foundExistingRateInNewMode = rateInForm.getId() == null && rateInDb != null;
		boolean foundExistingRateInEditMode = rateInForm.getId() != null && rateInForm.getId() != rateInDb.getId();
		
		if (foundExistingRateInEditMode || foundExistingRateInNewMode) {
			throw new ShippingRateAlreadyExistsException("There's already a rate for the destination " + rateInForm.getCountry().getName() + ", " + rateInForm.getState());
		}
		
		repository.save(rateInForm);
	}
	
	public ShippingRates get(Integer id) throws ShippingRateAlreadyExistsException {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ShippingRateAlreadyExistsException("Could not find any shipping rate with ID " + id);
		}
	}
	
	public void updateCODSupport(Integer id, boolean codSupported) throws ShippingRateAlreadyExistsException {
		Long count = repository.countById(id);
		if (count == null || count == 0) {
			throw new ShippingRateAlreadyExistsException("Could not find any shipping rate with ID " + id);
		}
		repository.updateCODSuported(id, codSupported);
	}
	
	public void delete(Integer id) throws ShippingRateAlreadyExistsException {
		Long count = repository.countById(id);
		if (count == null || count == 0) {
			throw new ShippingRateAlreadyExistsException("Could not find any shipping rate with ID " + id);
		}
		repository.deleteById(id);
	}
}
