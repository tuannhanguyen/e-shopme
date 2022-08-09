package com.shopme.address;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;

@Service
@Transactional
public class AddressService {

	@Autowired private AddressRepository addressRepository;
	
	public List<Address> findByCustomer(Customer customer){
		return addressRepository.findByCustomer(customer);
	}
	
	public void save(Address address) {
		addressRepository.save(address);
	}
	
	public Address get(Integer id, Integer customerId) {
		return addressRepository.findByIdAndCustomer(id, customerId);
	}
	
	public void delete(Integer id, Integer customerId) {
		addressRepository.deleteByIdAndCustomer(id, customerId);
	}
	
	public void setDefaultAddress(Integer addressId, Integer customerId) {
		if (addressId > 0) {
			addressRepository.setDefaultAddress(addressId);
		}
		addressRepository.setNontDefaultForOther(addressId, customerId);
	}
}
