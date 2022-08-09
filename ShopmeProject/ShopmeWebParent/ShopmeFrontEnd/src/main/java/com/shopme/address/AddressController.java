package com.shopme.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.Utility;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerService;
import com.shopme.security.CustomerNotFoundException;

@Controller
public class AddressController {

	@Autowired private AddressService addressService;
	@Autowired private CustomerService customerService;
	
	@GetMapping("/address_book")
	public String showAddressBook(Model model, HttpServletRequest request) throws CustomerNotFoundException {
		
		Customer customer = getAuthenticatedCustomer(request);
		List<Address> listAddresses = addressService.findByCustomer(customer);
		
		boolean usePrimaryAddressAsDefault = true;
		
		for (Address address : listAddresses) {
			if (address.isDefaultForShipping()) {
				usePrimaryAddressAsDefault = false;
				break;
			}
		}
		
		
		model.addAttribute("listAddresses", listAddresses);
		model.addAttribute("customer", customer);
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		
		return "address_book/addresses";
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
		String email = Utility.getEmailOrAuthenticatedCustomer(request);
		if (email == null) {
			throw new CustomerNotFoundException("No Authenticated customer");
		}
		
		return customerService.getCustomerByEmail(email);
	}
	
	@GetMapping("/add_new_address")
	public String viewAddressForm(Model model) {
		Address address = new Address();
		
		List<Country> listAllCountries = customerService.listAllCountry();
		model.addAttribute("listAllCountries", listAllCountries);
		model.addAttribute("pageTitle", "Add New Address");
		model.addAttribute("address", address);
		return "address_book/address_form";
	}
	
	@PostMapping("/add_new_address")
	public String addNewAddress(HttpServletRequest request, Address address, RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		Customer customer = getAuthenticatedCustomer(request);
		address.setCustomer(customer);
		
		addressService.save(address);
		
		redirectAttributes.addFlashAttribute("message", "The address has been saved successfully.");
		
		return "redirect:/address_book";
	}
	
	@GetMapping("/address_book/edit/{id}")
	public String edit(@PathVariable("id") Integer id, HttpServletRequest request, Model model) throws CustomerNotFoundException {
		Customer customer = getAuthenticatedCustomer(request);
		Address address = addressService.get(id, customer.getId());
		List<Country> listAllCountries = customerService.listAllCountry();
		
		model.addAttribute("listAllCountries", listAllCountries);
		model.addAttribute("address", address);
		model.addAttribute("pageTitle", "Edit Address (ID: " + id + ")");
		
		return "address_book/address_form";
		
	}
	
	@GetMapping("/address_book/delete/{id}")
	public String delete(@PathVariable("id") Integer id, HttpServletRequest request, RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		
		Customer customer = getAuthenticatedCustomer(request);
		addressService.delete(id, customer.getId());
		
		redirectAttributes.addFlashAttribute("message", "The address has been deleted.");
		return "redirect:/address_book";
	}
	
	@GetMapping("/address_book/default/{id}")
	public String setDefaultAddress(@PathVariable("id") Integer id, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		
		Customer customer = getAuthenticatedCustomer(request);
		addressService.setDefaultAddress(id, customer.getId());
		redirectAttributes.addFlashAttribute("message", "The address has been set as default.");
		
		return "redirect:/address_book";
	}
}
