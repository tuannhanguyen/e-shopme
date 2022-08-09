package com.shopme.address;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AddressRepositoryTest {

	@Autowired private AddressRepository repository;
	
//	@Test
//	public void testCreateAddress() {
//		Integer customerId = 5;
//		Integer countryId = 234;
//		
//		Address address = new Address();
//		
//		address.setCustomer(new Customer(customerId));
//		address.setCountry(new Country(countryId));
//		address.setFirstName("John");
//		address.setLastName("Biden");
//		address.setPhoneNumber("192-192-122");
//		address.setAddressLine1("587 KVC");
//		address.setCity("Thu Duc");
//		address.setState("Linh Dong");
//		address.setPhoneNumber("028 928 1274");
//		address.setPostalCode("929111");
//		//address.setDefaultForShipping(true);
//		
//		Address savedAddress = repository.save(address);
//		
//		assertThat(savedAddress.getId()).isNotNull();
//		
//	}
	
//	@Test
//	public void testFindByCustomer() {
//		Integer customerId = 5;
//		List<Address> listAddresses = repository.findByCustomer(new Customer(customerId));
//		assertThat(listAddresses.size()).isGreaterThan(0);
//		
//		listAddresses.forEach(System.out :: println);
//	}
	
//	@Test
//	public void testFindByIdAndCustomer() {
//		Integer addressId = 1;
//		Integer customerId = 5;
//		
//		Address address = repository.findByIdAndCustomer(addressId, customerId);
//		
//		assertThat(address).isNotNull();
//		System.out.println(address);
//		
//	}
	
//	@Test
//	public void testUpdate() {
//		Integer addressId = 1;
//		String phoneNumber = "082 757 1386";
//		
//		Address address = repository.findById(addressId).get();
//		address.setPhoneNumber(phoneNumber);
//		
//		Address savedAddress = repository.save(address);
//		
//		assertThat(savedAddress.getPhoneNumber().equals(phoneNumber));
//		
//	}
	
//	@Test
//	public void testDeleteByIdAndCustomer() {
//		Integer addressId = 1;
//		Integer customerId = 5;
//		
//		repository.deleteByIdAndCustomer(addressId, customerId);
//		
//	}
	
//	@Test
//	public void testSetDefaultAddress() {
//		Integer id = 2;
//		repository.setDefaultAddress(id);
//		Address address = repository.findById(id).get();
//		
//		assertThat(address.isDefaultForShipping()).isTrue();
//		
//	}
	
//	@Test
//	public void testSetNonDefaultAddress() {
//		Integer id = 18;
//		Integer customerId = 1;
//		repository.setDefaultAddress(id);
//		repository.setNontDefaultForOther(id, customerId);
//		Address address = repository.findById(id).get();
//		
//		//assertThat(address.isDefaultForShipping()).isFalse();
//		
//	}
}
