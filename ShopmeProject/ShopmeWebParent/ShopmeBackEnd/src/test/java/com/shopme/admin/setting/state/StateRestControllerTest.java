package com.shopme.admin.setting.state;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;

@SpringBootTest
@AutoConfigureMockMvc
public class StateRestControllerTest {
	
	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	@Autowired CountryRepository countryRepository;
	@Autowired StateRepository stateRepository;
	
	@Test
	@WithMockUser(username = "tuannhan", password = "nhan2020", roles = "ADMIN")
	public void testCreateState() throws JsonProcessingException, Exception {
		Country country = countryRepository.findById(3).get();
		State state = new State("Liverpool", country);
		
		String url = "/states/save";
		
		MvcResult result = mockMvc.perform(post(url)
							.contentType("application/json")
							.content(objectMapper.writeValueAsString(state))
							.with(csrf()))
						.andDo(print())
						.andExpect(status().isOk())
						.andReturn();
		
		String response = result.getResponse().getContentAsString();
//		State getState = objectMapper.readValue(response, State.class);
		System.out.println("Response: " + response);
		
	}
	
	
	

}
