package com.shopme.admin.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
	
	@Query("select c from Customer c where c.email = ?1 ")
	public Customer findByEmail(String email);

	@Query("update Customer c set c.enabled = ?2 where c.id = ?1")
	@Modifying
	public void enable(Integer id, boolean enable);
	
	@Query("select c from Customer c where c.firstName like %?1% "
			+ "or c.lastName like %?1%"
			+ "or c.email like %?1%"
			+ "or c.addressLine1 like %?1%"
			+ "or c.addressLine2 like %?1%"
			+ "or c.city like %?1%"
			+ "or c.state like %?1%"
			+ "or c.postalCode like %?1%"
			+ "or c.country.name like %?1%")
	public Page<Customer> findAll(String keyword, Pageable pageable);
	
}
