package com.shopme.shoppingcart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.Utility;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerService;
import com.shopme.security.CustomerNotFoundException;

@Controller
public class ShoppingCartController {

	@Autowired ShoppingCartService shoppingCartService;
	@Autowired private CustomerService customerService;
	
	@GetMapping("/cart")
	public String viewShoppingCart(Model model, HttpServletRequest request) throws CustomerNotFoundException {
		
		//authenticated Customer
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> listCartItems = shoppingCartService.listCartItems(customer);
		
		model.addAttribute("listCartItems", listCartItems);
		
		float estimatedTotal = 0.0F;
		for(CartItem item : listCartItems) {
			estimatedTotal += item.getSubTotal();
		}
		model.addAttribute("estimatedTotal", estimatedTotal);
		return "cart/shopping_cart";
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
		String email = Utility.getEmailOrAuthenticatedCustomer(request);
		if (email == null) {
			throw new CustomerNotFoundException("No Authenticated customer");
		}
		
		return customerService.getCustomerByEmail(email);
	}
	
	
}
