package com.mathdev.quickbite.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.RestaurantDTO;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.repositories.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepository repo;
	
	//GET SERVICES
	public List<RestaurantDTO> findAll(){
		List<Restaurant> users = repo.findAll();
		
		return users.stream().map(this::toDTO).toList();
	}
	
	public RestaurantDTO findById(Long id) {
		Restaurant obj = repo.findById(id).orElseThrow(()->new RuntimeException("User not found!"));
		
		return toDTO(obj);
	}
	
	
	//POST SERVICES
	public RestaurantDTO insert(RestaurantDTO dto) {
		Restaurant obj = fromDTO(dto);
		obj = repo.save(obj);
		
		return toDTO(obj);
	}
	
	//DELETE SERVICES
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
	
	//PUT SERVICES
	public RestaurantDTO update(Long id, RestaurantDTO newData) {
		Restaurant entity =  repo.getReferenceById(id);
		updateData(entity, newData);
		
		return toDTO(repo.save(entity));
	}
	
	//AUXILIARY METHODS
	private void updateData(Restaurant entity, RestaurantDTO newData) {
		entity.setName(newData.name());
		entity.setAddress(newData.address());
	}
	
	private Restaurant fromDTO(RestaurantDTO dto) {
		return new Restaurant(dto.id(),dto.name(),dto.address());
	}
	
	private RestaurantDTO toDTO(Restaurant entity) {
		return new RestaurantDTO(entity.getId(),entity.getName(),entity.getAddress());
	}
}
