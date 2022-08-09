package com.shopme.admin.setting.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {
	
	@Autowired private StateRepository stateRepository;
	
	@Autowired private CountryRepository countryRepository;
	
//	@Test
//	public void testCreateStateInIndia() {
//		Country country = countryRepository.findById(1).get();
//		State state1 = new State("Karnataka");
//		state1.setCountry(country);
//		
//		State state2 = new State("Punjab");
//		state1.setCountry(country);
//		State state3 = new State("Tamil Nadu");
//		state3.setCountry(country);
//		State state4 = new State("West Bengal");
//		state4.setCountry(country);
//		
//		stateRepository.saveAll(List.of(state1, state2, state3, state4));
//		
//	}
//	
	@Test
	public void testCreateStateInUS() {
		Country country = countryRepository.findById(2).get();
		State state1 = new State("California");
		state1.setCountry(country);
		
		State state2 = new State("Texas");
		state2.setCountry(country);
		State state3 = new State("New York");
		state3.setCountry(country);
		
		stateRepository.saveAll(List.of(state1, state2, state3));
	}

//	@Test
//	public testUpdateCountry() {
//		
//	}
}
