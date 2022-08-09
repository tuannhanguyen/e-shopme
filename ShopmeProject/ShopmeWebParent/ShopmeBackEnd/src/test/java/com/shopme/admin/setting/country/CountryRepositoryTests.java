package com.shopme.admin.setting.country;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Country;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {

	@Autowired private CountryRepository countryRepository;
	
//	@Test
//	public void testCreateCountry() {
//		Country country = countryRepository.save(new Country("Japan", "JP"));
//		
//		assertThat(country).isNotNull();
//		assertThat(country.getId()).isGreaterThan(0);
//	}
	
//	@Test
//	public void findAll() {
//		List<Country> countries = countryRepository.findAllByOrderByNameAsc();
//		
//		countries.forEach(country -> {
//			System.out.println(country.getName());
//		});
//	}
	
//	@Test
//	public void testUpdateCountry() {
//		Country country1 = countryRepository.findById(1).get();
//		country1.setName("Republic of India");
//		country1.setCode("IN");
//		
//		Country country2 = countryRepository.findById(2).get();
//		country2.setName("United States");
//		country2.setCode("US");
//		
//		Country country3 = countryRepository.findById(3).get();
//		country3.setName("United Kingdom");
//		country3.setCode("UK");
//		
//		Country country4 = countryRepository.findById(4).get();
//		country4.setName("Vietnam");
//		country4.setCode("VN");
//		
//	}
	
//	@Test void testDeleteCountry() {
//		countryRepository.deleteById(5);
//		countryRepository.deleteById(6);
//	}
	
}
