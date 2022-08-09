package com.shopme.customer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
	@Query("select c from Customer c where c.email = ?1")
	public Customer findByEmail(String email);

	@Query("select c from Customer c where c.verificationCode = ?1")
	public Customer findByVerificationCode(String verificationCode);
	
	@Query("UPDATE Customer c SET c.enabled = true, c.verificationCode = null WHERE c.email=?1")
	@Modifying
	public void enable(String email);
	
	@Query("UPDATE FROM Customer c SET c.authenticationType = ?1 where c.id = ?2")
	@Modifying
	public void updateAuthenticationType(AuthenticationType type, Integer id);
	
	@Query("SELECT c from Customer c where c.resetPasswordToken = ?1")
	public Customer findByResetPasswordToken(String resetPasswordToken);
}
