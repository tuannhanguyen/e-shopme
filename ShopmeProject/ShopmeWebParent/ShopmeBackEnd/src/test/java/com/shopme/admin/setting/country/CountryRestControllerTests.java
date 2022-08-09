package com.shopme.admin.setting.country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.common.entity.Country;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryRestControllerTests {
	@Autowired MockMvc mockMvc; 
	
	@Autowired ObjectMapper objectMapper;
	
	@Autowired CountryRepository repository;
	
//	@Test
//	@WithMockUser(username = "nam@codejava.net", password = "nam202020", roles = "ADMIN") // username va pwd co gia tri bat ky 
//	public void testListCountries() throws Exception {
//		String url = "/countries/list";
//		// perform get Req to server
//		MvcResult result = mockMvc.perform(get(url))
//				.andExpect(status().isOk())
//				.andDo(print())
//				.andReturn();
//		
//		// read json data from response body
//		String jsonResponse = result.getResponse().getContentAsString();
//		System.out.println("Response: " + jsonResponse);
//		
//		//read json data from response server convert to java object
////		Country[] countries = objectMapper.readValue(jsonResponse, Country[].class);
////		for (Country country : countries) {
////			System.out.println(country);
////		}
//		
//		//assertThat(countries).hasSizeGreaterThan(0);
//	}
	
//	@Test
//	@WithMockUser(username = "nam@codejava.net", password = "nam202020", roles = "ADMIN")
//	public void testCreateCountry() throws JsonProcessingException, Exception {
//		String url = "/countries/save";
//		Country country = new Country("Japan", "JP");
//		
//		MvcResult result = mockMvc.perform(post(url).contentType("application/json")
//				.content(objectMapper.writeValueAsString(country))
//				.with(csrf()))ssazzzzzzaaAA`
//				
//			.andDo(print())
//			.andExpect(status().isOk())
//			.andReturn();
//		
//		String response = result.getResponse().getContentAsString();
//		System.out.println("CountryID: " + response);
//		
//		Integer countryId = Integer.parseInt(response);
//		Country savedCountry = repository.findById(countryId).get();
//		
//		assertThat(savedCountry.getName()).isEqualTo("Japan");
//	}
//	
//	@Test
//	@WithMockUser(username = "nam@codejava.net", password = "nam202020", roles = "ADMIN")
//	public void testUpdateCountry() throws JsonProcessingException, Exception {
//		String url = "/countries/save";
//		Integer countryID = 7;
//		Country country = new Country(countryID, "China", "CN");
//		
//		MvcResult result = mockMvc.perform(post(url).contentType("application/json")
//				.content(objectMapper.writeValueAsString(country))
//				.with(csrf()))
//				
//			.andDo(print())
//			.andExpect(status().isOk())
//			.andReturn();
//		
//		String response = result.getResponse().getContentAsString();
//		
//		Integer countryId = Integer.parseInt(response);
//		Country savedCountry = repository.findById(countryId).get();
//		
//		assertThat(savedCountry.getName()).isEqualTo("China");
//	}
//	
//	@Test
//	@WithMockUser(username = "nam@codejava.net", password = "nam202020", roles = "ADMIN")
//	public void testDeleteCountry() throws Exception {
//		Integer countryId = 12;
//		String url = "/countries/delete/" + countryId;
//		mockMvc.perform(get(url))
//			.andDo(print())
//			.andExpect(status().isOk());
//		
//		Optional<Country> country = repository.findById(countryId);
//		assertThat(country).isNotPresent();
//	}
}
