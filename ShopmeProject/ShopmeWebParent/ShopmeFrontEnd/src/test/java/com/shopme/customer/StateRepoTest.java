package com.shopme.customer;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import com.shopme.setting.CountryRepository;
import com.shopme.setting.StateRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace =  Replace.NONE)
@Rollback(false)
public class StateRepoTest {

	@Autowired private StateRepository stateRepository;
	@Autowired private CountryRepository countryRepository;
	
	@Test
	public void testListStateByCountry() {
		
		Country country = countryRepository.findById(242).get();
		List<State> states = stateRepository.findByCountryOrderByNameAsc(country);
		
		states.forEach(state ->System.out.println(state.getName()));
	}
}
