package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	Country country;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Customer customer;
	
	@Column(name = "first_name", length = 45, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 45, nullable = false)
	private String lastName;
	
	@Column(name = "phone_number", length = 15, nullable = false)
	private String phoneNumber;
	
	@Column(length = 64, nullable = false)
	private String addressLine1;
	
	@Column(length = 64)
	private String addressLine2;
	
	@Column(length = 45, nullable = false)
	private String city;
	
	@Column(length = 45, nullable = false)
	private String state;
	
	@Column(name = "postal_code", length = 10, nullable = false)
	private String postalCode;
	
	@Column(name = "default_address")
	boolean defaultForShipping;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isDefaultForShipping() {
		return defaultForShipping;
	}

	public void setDefaultForShipping(boolean defaultForShipping) {
		this.defaultForShipping = defaultForShipping;
	}
	
	

	@Override
	public String toString() {
		String address = firstName;
		
		if(lastName != null || !lastName.isEmpty()) address += " " + lastName;
		
		if(!addressLine1.isEmpty()) address += ", " + addressLine1;
		
		if(addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;
		
		if(!city.isEmpty()) address += ", " + city + ", ";
		
		if(state != null && !state.isEmpty()) address += " " + state + ", ";
		
		address += country.getName();
		
		if(!postalCode.isEmpty()) address += ". Postal Code: " + postalCode;
		if(!phoneNumber.isEmpty()) address += ". Phone Numer: " + phoneNumber;
		
		return address;
	
	}
}
