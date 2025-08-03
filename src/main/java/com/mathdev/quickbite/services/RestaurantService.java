package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.ProductDTO;
import com.mathdev.quickbite.dto.RestaurantDTO;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.repositories.ProductRepository;
import com.mathdev.quickbite.repositories.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepository repo;
	
	@Autowired
	ProductRepository productRepository;
	//GET SERVICES
	public List<RestaurantDTO> findAll(){
		List<Restaurant> restaurants = repo.findAll();
		
		return restaurants.stream().map(this::toDTO).collect(Collectors.toList()); //fitful for Java < 16 versions
	}
	
	public RestaurantDTO findById(Long id) {
		Restaurant obj = repo.findById(id).orElseThrow(()->new RuntimeException("Restaurant not found!"));
		
		return toDTO(obj);
	}
	
	public List<ProductDTO> findProductsByRestaurant(Long restaurantId) {
		if (!repo.existsById(restaurantId)) {
		    throw new RuntimeException("Restaurant not found");
		}

	    List<Product> products = productRepository.findByRestaurantId(restaurantId);

	    return products.stream()
	            .map(product -> new ProductDTO(
	                    product.getId(),
	                    product.getName(),
	                    product.getDescription(),
	                    product.getBasePrice(),
	                    product.getRestaurant().getId()))
	            .collect(Collectors.toList());
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
		return new Restaurant(dto.id(),dto.name(),null,null,dto.address());
	}
	
	private RestaurantDTO toDTO(Restaurant entity) {
		return new RestaurantDTO(entity.getId(),entity.getName(),entity.getEmail(),entity.getAddress());
	}
}
