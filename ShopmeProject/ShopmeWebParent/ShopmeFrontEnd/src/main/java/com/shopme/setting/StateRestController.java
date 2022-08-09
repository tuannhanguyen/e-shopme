package com.shopme.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import com.shopme.common.entity.StateDTO;

@RestController
public class StateRestController {
	
	@Autowired private StateRepository stateRepository;
	
	@GetMapping("/settings/list_states_by_countries/{id}")
	public List<StateDTO> listStatesByCountry(@PathVariable("id") Integer id){
		List<StateDTO> stateDTOs = new ArrayList<>();
		List<State> countries = stateRepository.findByCountryOrderByNameAsc(new Country(id));
		for(State item : countries ) {
			stateDTOs.add(new StateDTO(item.getId(), item.getName()));
		}
		return stateDTOs;
	}

}
