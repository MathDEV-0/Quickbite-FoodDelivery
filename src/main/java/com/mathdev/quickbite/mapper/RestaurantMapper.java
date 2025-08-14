package com.mathdev.quickbite.mapper;

import com.mathdev.quickbite.dto.RestaurantDTO;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.repositories.RestaurantRepository;
import com.mathdev.quickbite.services.exceptions.ResourceNotFoundException;


public class RestaurantMapper {
	
	public static Restaurant fromDTOInsert(RestaurantDTO dto) {
	    return new Restaurant(null, dto.name(), dto.email(), "senha_padrao_ou_hash", dto.address());
	}

	public static Restaurant fromDTOUpdate(RestaurantDTO dto, RestaurantRepository repo) {
	    Restaurant temp = repo.findById(dto.id())
	        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
	    String password = temp.getPassword();
	    return new Restaurant(dto.id(), dto.name(), dto.email(), password, dto.address());
	}


	public static RestaurantDTO toDTO(Restaurant entity) {
		return new RestaurantDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getAddress());
	}
}
