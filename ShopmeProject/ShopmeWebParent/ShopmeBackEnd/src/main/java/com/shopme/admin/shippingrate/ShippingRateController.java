package com.shopme.admin.shippingrate;


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
import com.shopme.common.entity.ShippingRates;

@Controller
public class ShippingRateController {

	@Autowired ShippingRateService rateService;
	
	@GetMapping("/shipping_rates")
	public String listFirstPage(Model model) {
		return listByPage(model, 1, null, "asc", "country");
	}
	
	@GetMapping("/shipping_rates/page/{pageNum}")
	public String listByPage(Model model, @PathVariable("pageNum") int pageNum, @Param("keyword") String keyword,
			 @Param("sortDir") String sortDir, @Param("sortField") String sortField) {
		
		Page<ShippingRates> page = rateService.listByPage(pageNum, keyword, sortDir, sortField);
		List<ShippingRates> listShippingRates = page.getContent();
		
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages(); 
		int startCount = (pageNum-1) * ShippingRateService.RATES_PER_PAGE + 1;
		long endCount = startCount + ShippingRateService.RATES_PER_PAGE - 1;
		
		if(endCount > totalItems) {
			endCount = totalItems;
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "esc" : "asc";
		String moduleURL = "/shipping_rates";
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("sortField", sortField);
		model.addAttribute("keyword", keyword);
		model.addAttribute("listShippingRates", listShippingRates);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("moduleURL", moduleURL);
		
		return "shipping_rates/shipping_rates";
	}
	
	@GetMapping("/shipping_rates/new")
	public String newRate(Model model) {
		ShippingRates rate = new ShippingRates();
		List<Country> listAllCountries = rateService.listAllCountries();
		
		model.addAttribute("pageTitle", "New Rate");
		model.addAttribute("rate", rate);
		model.addAttribute("listAllCountries", listAllCountries);
		return "shipping_rates/shipping_rate_form";
		
	}
	
	@PostMapping("/shipping_rates/save")
	public String saveRate(ShippingRates rate, RedirectAttributes redirectAttributes) {
		try {
			rateService.save(rate);
			redirectAttributes.addFlashAttribute("message", "The shipping rate has been saved successfully.");
		} catch (ShippingRateAlreadyExistsException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/shipping_rates";
	}
	
	@GetMapping("/shipping_rates/edit/{id}")
	public String editRate(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			ShippingRates rates = rateService.get(id);
			List<Country> listAllCountries = rateService.listAllCountries();
			
			model.addAttribute("pageTitle", "Edit Rate");
			model.addAttribute("listAllCountries", listAllCountries);
			model.addAttribute("rate", rates);
		} catch (ShippingRateAlreadyExistsException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "shipping_rates/shipping_rate_form";
	}
	
	@GetMapping("/shipping_rates/cod/{id}/enabled/{supported}")
	public String updateCODSupport(@PathVariable("id") Integer id, @PathVariable("supported") boolean supported,
			RedirectAttributes redirectAttributes) {
		
		try {
			rateService.updateCODSupport(id, supported);
			String status = supported ? "enabled" : "disabled";
			redirectAttributes.addFlashAttribute("message", "The COD support has been " + status);
		} catch (ShippingRateAlreadyExistsException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/shipping_rates";
	}
	
	@GetMapping("/shipping_rates/delete/{id}")
	public String deleteRate(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		
		try {
			rateService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The shipping rate ID " + id + " has been deleted");
		} catch (ShippingRateAlreadyExistsException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/shipping_rates";
	}
	
}
