package com.shopme.admin.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.desktop.AppReopenedEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTest {

	@Autowired private CustomerRepository repository;
	@Autowired TestEntityManager entityManager;
	
	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer();
		
		Integer countryId = 234;
		Country country = entityManager.find(Country.class, countryId);
		
		customer.setEmail("tuannhan6@gmail.net");
		customer.setFirstName("tuannhan");
		customer.setLastName("nguyen");
		customer.setPassword("admin");
		customer.setPhoneNumber("838338");
		customer.setAddressLine1("HCM");
		customer.setAddressLine2("TD");
		customer.setCity("HCM");
		customer.setState("LosAngeles");
		customer.setCountry(country);
		customer.setPostalCode("123332");
		customer.setVerificationCode("AS91q");
		customer.setCreatedTime(new Date());
		
		Customer savedCustomer = repository.save(customer);
		
		
		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}
	
//	@Test
//	public void testListCustomer() {
//		List<Customer> customers = (List<Customer>) repository.findAll();
//		
//		customers.forEach(customer ->{
//			System.out.println(customer);
//		});
//	}
	
//	@Test
//	public void testUpdateCustomer() {
//		Customer customer = repository.findById(1).get();
//		
//		String lastName = "Nguyen Vo"; 
//		
//		customer.setEmail("tuannhan6@gmail.com");
//		customer.setLastName(lastName);
//		customer.setEnabled(true);
//		
//		repository.save(customer);
//		
//		assertThat(customer.getLastName()).isEqualTo(lastName);
//	}
	
//	@Test
//	public void testFindByEmail() {
//		String email = "tuannhan6@gmail.com";
//		Customer customer = repository.findByEmail(email);
//		
//		System.out.println(customer);
//		
//		assertThat(customer).isNotNull();
//	}
	
//	@Test
//	public void testEnableStatus() {
//		Integer id = 2;
//		repository.enable(id);
//		
//		Customer customer = repository.findById(id).get();
//		
//		assertThat(customer.isEnabled()).isTrue();
//	}
	
	@Test
	public void testDeleteCustomer() {
		Integer id = 1;
		repository.deleteById(id);
		
		Optional<Customer> customer = repository.findById(id);
		
		assertThat(customer).isNotPresent();
	}
}
