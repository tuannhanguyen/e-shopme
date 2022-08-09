package com.shopme.admin.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerRestController {

	@Autowired private CustomerService customerService;
	
	@PostMapping("/customer/check_unique_email")
	public String checkEmailUnique(String email, Integer id) {
		return customerService.isEmailUnique(email, id);
	}
}
