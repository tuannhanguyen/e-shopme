package com.shopme.admin.customer;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;


@Service
@Transactional
public class CustomerService {
	
	@Autowired private CustomerRepository customerRepository;
	@Autowired private CountryRepository countryRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	public static final int CUSTOMER_PER_PAGE = 10;
	
	
	public List<Customer> listAll(){
		return (List<Customer>) customerRepository.findAll();
	}
	
	public Page<Customer> listByPage(int pageNum, String keyword, String sortDir, String sortField){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMER_PER_PAGE, sort);
		
		if (keyword != null) {
			return customerRepository.findAll(keyword, pageable);
		}
		
		return customerRepository.findAll(pageable);
	}
	
	public Customer get(Integer id) throws CustomerNotFoundException {
		try {
			return customerRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CustomerNotFoundException("Could not found any customer with ID" + id);
		}
	}
	
	public void enableStatus(Integer id, boolean enable) throws CustomerNotFoundException {
		try {
			Customer customer = customerRepository.findById(id).get();
			customerRepository.enable(id, enable);
		} catch (NoSuchElementException e) {
			throw new CustomerNotFoundException("Could not find any Customer with ID " + id); 
		}
	}
	
	public void delete(Integer id) throws CustomerNotFoundException {
		try {
			Customer customer = customerRepository.findById(id).get();
			customerRepository.deleteById(id);
		} catch (NoSuchElementException e) {
			throw new CustomerNotFoundException("Could not find any Customer with ID " + id); 
		}
	}
	
	public List<Country> listAllCountry(){
		return (List<Country>) countryRepository.findAllByOrderByNameAsc();
	}
	
	public String isEmailUnique(String email, Integer customerExistingId) {
		Customer customer = customerRepository.findByEmail(email);
		
		if(customer != null) {
			if(customer.getId() != customerExistingId)
			return "Duplicated Email";
		}
		return "OK";
	}
	
	public void saveUpdate(Customer customerInform) {
		
		Customer customerExisting = customerRepository.findById(customerInform.getId()).get();
		customerInform.setCreatedTime(customerExisting.getCreatedTime());
		customerInform.setVerificationCode(customerExisting.getVerificationCode());
		customerInform.setAuthenticationType(customerExisting.getAuthenticationType());
		
		if (customerInform.getPassword().isEmpty()) {
			customerInform.setPassword(customerExisting.getPassword());
			customerRepository.save(customerInform);
		} else {
			encodedePassword(customerInform);
			customerRepository.save(customerInform);
		}
	}

	private void encodedePassword(Customer customer) {
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
		
	}


}
