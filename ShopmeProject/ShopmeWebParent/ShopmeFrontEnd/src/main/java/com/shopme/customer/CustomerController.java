package com.shopme.customer;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.Utility;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.security.CustomerDetails;
import com.shopme.security.oauth2.CustomerOAuth2User;
import com.shopme.setting.EmailSettingBag;
import com.shopme.setting.SettingService;

@Controller
public class CustomerController {

	@Autowired private CustomerService customerService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		List<Country> listAllCountries = customerService.listAllCountry();
		model.addAttribute("listAllCountries", listAllCountries);
		model.addAttribute("pageTitle", "Customer Registration");
		model.addAttribute("customer", new Customer());
		
		return "register/register_form";
	}
	
	@PostMapping("/create_customer")
	public String createCustomer(Customer customer, Model model, 
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		customerService.register(customer);
		sendVerificationEmail(request, customer);
		
		model.addAttribute("pageTitle", "Registration Succeeded!");
		
		return "register/register_success";
	}

	private void sendVerificationEmail(HttpServletRequest request, Customer customer) 
			throws UnsupportedEncodingException, MessagingException {
		
		EmailSettingBag emailSettings = settingService.getEmailSettings();
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) Utility.prepareMailSender(emailSettings);
		
		String toAddress = customer.getEmail();
		String subject = emailSettings.getCustomerVerifySubject();
		String content = emailSettings.getCustomerVerifyContent();
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);
		
		content = content.replace("[[name]]", customer.getFullName());
		
		String verifyURL = Utility.getSiteUrl(request) + "/verify?code=" + customer.getVerificationCode();
		
		content = content.replace("[[URL]]", verifyURL);
		
		helper.setText(content, true);
		
		mailSender.send(message);
		
		System.out.println("to address: " + toAddress);
		System.out.println("verify URL: " + verifyURL);
	}
	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code) {
		boolean verified = customerService.verify(code);
		if (verified) {
			return "register/verify_success";
		} else {
			return "register/verify_fail";
		}
	}
	
	@GetMapping("/account_details")
	public String viewAccountDetails(Model model, HttpServletRequest request) {

		String email = Utility.getEmailOrAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);
		List<Country> listAllCountries = customerService.listAllCountry();
		
		model.addAttribute("customer", customer);
		model.addAttribute("listAllCountries", listAllCountries);
		
		
		
		//UsernamePasswordAuthenticationToken
		//OAuth2AuthenticationToken
		//RememberMeAuthenticationToken
		return "customer/account_form";
	}
	
	
	
	
	@PostMapping("/update_account_details")
	public String updateAccountDetails(Model model, Customer customer,HttpServletRequest request,  RedirectAttributes redirectAttributes) {

		customerService.updateCustomer(customer);
		redirectAttributes.addFlashAttribute("message", "Your account details have been updated");
		
		updateNameForAuthenticatedCustomer(customer, request);
		
		return "redirect:/account_details";
	}

	private void updateNameForAuthenticatedCustomer(Customer customer, HttpServletRequest request) {
		
		Object principal = request.getUserPrincipal();
		String fullName = null;
		if (principal instanceof UsernamePasswordAuthenticationToken ||
				principal instanceof RememberMeAuthenticationToken) {
			CustomerDetails customerDetails = getCustomerDetailsObject(principal);
			Customer authenticatedCustomer = customerDetails.getCustomer();
			authenticatedCustomer.setFirstName(customer.getFirstName());
			authenticatedCustomer.setLastName(customer.getLastName());
			
		} else if (principal instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) principal;
			CustomerOAuth2User oAuth2User = (CustomerOAuth2User) oAuth2Token.getPrincipal();
			fullName = customer.getFirstName() + " " + customer.getLastName();
			oAuth2User.setFullName(fullName);
		}
	}
	
	private CustomerDetails getCustomerDetailsObject(Object principal) {
		CustomerDetails customerDetails = null;
		if (principal instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
			customerDetails = (CustomerDetails) token.getPrincipal();
		} else if(principal instanceof RememberMeAuthenticationToken) {
			RememberMeAuthenticationToken token = (RememberMeAuthenticationToken) principal;
			customerDetails = (CustomerDetails) token.getPrincipal();
		}
		return customerDetails;
	}
}
