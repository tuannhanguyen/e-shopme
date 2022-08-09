package com.shopme.customer;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.security.CustomerNotFoundException;
import com.shopme.setting.CountryRepository;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class CustomerService {
	
	@Autowired private CountryRepository countryRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<Country> listAllCountry(){
		return countryRepository.findAllByOrderByNameAsc();
	}
	
	public String isEmailUnique(String email) {
		Customer customer = customerRepository.findByEmail(email);
		if (customer != null) {
			return "Duplicated Email";
		}
		return "OK";
	}
	
	public void register(Customer customer) {
		encodePassword(customer);
		customer.setEnabled(false);
		customer.setCreatedTime(new Date());
		customer.setAuthenticationType(AuthenticationType.DATABASE);
		
		String randomString = RandomString.make(64);
		customer.setVerificationCode(randomString);
		
		customerRepository.save(customer);
		
		System.out.println("Verification code: " + customer.getVerificationCode());
	}
	
	private void encodePassword(Customer customer) {
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
	}
	
	
	public Customer findByVerificationCode(String verificationCode) {
		return customerRepository.findByVerificationCode(verificationCode);
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public void setEnable(String email) {
		customerRepository.enable(email);
	}
	
	public boolean verify(String verificationCode) {
		Customer customer = customerRepository.findByVerificationCode(verificationCode);
		if(customer == null || customer.isEnabled()) {
			return false;
		} else {
			customerRepository.enable(customer.getEmail());
			return true;
		}
	}
	
	public void updateAuthenticationType(AuthenticationType type, Customer customer) {
		if(!customer.getAuthenticationType().equals(type)) {
			customerRepository.updateAuthenticationType(type, customer.getId());
		}
	}
	
	public void updateCustomer(Customer customerInForm) {
		Customer customerInDB = customerRepository.findById(customerInForm.getId()).get();
		customerInForm.setCreatedTime(customerInDB.getCreatedTime());
		if (customerInForm.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
			if (customerInForm.getPassword().isEmpty()) {
				customerInForm.setPassword(customerInDB.getPassword());
			} else {
				encodePassword(customerInForm);
			}
		} else {
			customerInForm.setPassword(customerInDB.getPassword());
		}
		customerRepository.save(customerInForm);
	}

	public void addNewCustomerUponOAuthLogin(String name, String email, String countryCode, 
			AuthenticationType authenticationType) {
		Customer customer = new Customer();
		
		setName(name, customer);
		
		customer.setEmail(email);
		customer.setEnabled(true);
		customer.setAuthenticationType(authenticationType);
		customer.setAddressLine1("");
		customer.setPassword("");
		customer.setCity("");
		customer.setState("");
		customer.setPostalCode("");
		customer.setPhoneNumber("");
		customer.setCountry(countryRepository.findByCode(countryCode));
		customer.setCreatedTime(new Date());
		
		customerRepository.save(customer);
	}
	
	private void setName(String name, Customer customer) {
		String[] nameArray = name.split(" ");
		if(nameArray.length < 2) {
			customer.setFirstName(name);
			customer.setLastName("");
		} else {
			String firstName = nameArray[0];
			customer.setFirstName(firstName);
			
			String lastName = name.replace(firstName, "");
			customer.setLastName(lastName.trim());
		}
	}

	public String updateResetPasswordToken(String email) throws CustomerNotFoundException {
		Customer customer = customerRepository.findByEmail(email);
		if (customer != null) {
			String token = RandomString.make(30);
			customer.setResetPasswordToken(token);
			customerRepository.save(customer);
			
			return token;
		}else {
			throw new CustomerNotFoundException("Could not find any customer with email " + email);
		}
	}
	
	public boolean getCustomerByResetPassword(String token) {
		Customer customer = customerRepository.findByResetPasswordToken(token);
		if (customer == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void updatePassword(String token, String newPassword) throws CustomerNotFoundException {
		Customer customer = customerRepository.findByResetPasswordToken(token);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found: Invalid Token" );
		}
		customer.setPassword(newPassword);
		encodePassword(customer);
		customer.setResetPasswordToken(null);
		
		customerRepository.save(customer);
	}
}
