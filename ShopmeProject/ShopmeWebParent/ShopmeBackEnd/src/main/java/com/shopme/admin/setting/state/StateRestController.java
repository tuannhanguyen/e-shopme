package com.shopme.admin.setting.state;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import com.shopme.common.entity.StateDTO;

@RestController
public class StateRestController {

	@Autowired private StateRepository stateRepository;
	
	@GetMapping("/states/list_by_countries/{id}")
	public List<StateDTO> listByCountry(@PathVariable("id") Integer id){
		List<State> states = stateRepository.findByCountryOrderByNameAsc(new Country(id));
		List<StateDTO> stateDTOs = new ArrayList<>();
		for(State state : states) {
			stateDTOs.add(new StateDTO(state.getId(), state.getName()));
		}
		return stateDTOs;
	}
	
	@PostMapping("/states/save")
	public String save(@RequestBody State state) {
		State savedState = stateRepository.save(state);
		
		return String.valueOf(savedState.getId());
	}
	
	@DeleteMapping("/states/country/delete/{id}")
	public void deleteState(@PathVariable("id") Integer id) {
		stateRepository.deleteById(id);
	}
}
