package com.shopme.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@Controller
public class CustomerController {
	@Autowired private CustomerService customerService;
	
	@GetMapping("/customers")
	public String listAll(Model model) {
		return listByPage(1, null, "firstName", "asc", model);
	}
	
	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum,
				@Param("keyword") String keyword,
				@Param("sortField") String sortField,
				@Param("sortDir") String sortDir, 
					Model model) {
		Page<Customer> page = customerService.listByPage(pageNum, keyword, sortDir, sortField);
		
		List<Customer> customers = page.getContent();
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		long startCount = (pageNum-1) * CustomerService.CUSTOMER_PER_PAGE + 1;
		long endCount = startCount + CustomerService.CUSTOMER_PER_PAGE - 1;
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		if (endCount > totalItems) {
			endCount = totalItems;
		}
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		
		model.addAttribute("customers", customers);
		model.addAttribute("moduleURL", "/customers");
		return "customer/customers";
	}

	@GetMapping("/customers/{id}/enabled/{enabled}")
	public String enableStatus(@PathVariable("id") Integer id , @PathVariable("enabled") boolean enabled,
			RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		try {
			customerService.enableStatus(id, enabled);
			String message = (enabled) ? "enabled" : "disabled";
			redirectAttributes.addFlashAttribute("message", "The customer ID " + id + " has been " + message);
		} catch (CustomerNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			customerService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The customer ID " + id + " has been deleted"); 
			return "redirect:/customers";
		} catch (CustomerNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage()); 
			return "redirect:/customers";
		}
	}
	
	@GetMapping("/customers/edit/{id}")
	public String editCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			Customer customer = customerService.get(id);
			List<Country> listAllCountries = customerService.listAllCountry();
			
			model.addAttribute("customer", customer);
			model.addAttribute("listAllCountries", listAllCountries);
			model.addAttribute("pageTitle", "Edit Customer (ID: " + id +" )");
			return "customer/customer_form";
		} catch (CustomerNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/customers";
		}
	}
	
	@GetMapping("/customers/details/{id}")
	public String customerDetails(@PathVariable("id") Integer id,
				Model model) throws CustomerNotFoundException {
		
		Customer customer = customerService.get(id);
		model.addAttribute("customer", customer);
		
		return "customer/customer_details_modal";
	}
	
	@PostMapping("/update_customer")
	public String updateCustomer(Customer customer, Model model, RedirectAttributes redirectAttributes) {
		customerService.saveUpdate(customer);
		redirectAttributes.addFlashAttribute("message", "The Customer ID " + customer.getId() + " has been updated");
		return "redirect:/customers";
	}
}
