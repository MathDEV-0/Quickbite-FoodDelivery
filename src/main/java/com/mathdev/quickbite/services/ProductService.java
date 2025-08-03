package com.mathdev.quickbite.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.ProductDTO;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.repositories.ProductRepository;
import com.mathdev.quickbite.repositories.RestaurantRepository;


@Service
public class ProductService {
	
	@Autowired
	ProductRepository repo;
	
	@Autowired
	private RestaurantRepository restaurantRepository; 

	
	//GET SERVICES
	public List<ProductDTO> findAll(){
		List<Product> users = repo.findAll();
		
		return users.stream().map(this::toDTO).toList();
	}
	
	public ProductDTO findById(Long id) {
		Product obj = repo.findById(id).orElseThrow(()->new RuntimeException("User not found!"));
		
		return toDTO(obj);
	}
	
	
	//POST SERVICES
	public ProductDTO insert(ProductDTO dto) {
		Product obj = fromDTO(dto);
		obj = repo.save(obj);
		
		return toDTO(obj);
	}
	
	//DELETE SERVICES
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
	
	//PUT SERVICES
	public ProductDTO update(Long id, ProductDTO newUser) {
		Product entity =  repo.getReferenceById(id);
		updateData(entity, newUser);
		
		return toDTO(repo.save(entity));
	}
	
	//AUXILIARY METHODS
	private void updateData(Product entity, ProductDTO newData) {
		entity.setName(newData.name());
		entity.setDescription(newData.description());
	}
	
	private Product fromDTO(ProductDTO dto) {
	    Restaurant restaurant = restaurantRepository.findById(dto.restaurantId())
	        .orElseThrow(() -> new RuntimeException("Restaurant not found"));
	    
	    return new Product(dto.id(), dto.name(), dto.description(), dto.basePrice(), restaurant);
	}

	
	private ProductDTO toDTO(Product entity) {
		return new ProductDTO(entity.getId(),entity.getName(),entity.getDescription(),entity.getBasePrice(),
				entity.getRestaurant().getId());
	}
}
