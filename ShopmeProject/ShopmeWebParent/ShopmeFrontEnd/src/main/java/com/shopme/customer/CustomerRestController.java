package com.shopme.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerRestController {

	@Autowired private CustomerService customerService;
	
	@PostMapping("/customer/check_unique_email")
	public String checkEmailUnique(String email) {
		return customerService.isEmailUnique(email);
	}
}
