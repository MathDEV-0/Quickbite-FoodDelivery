package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.ProductDTO;
import com.mathdev.quickbite.dto.RestaurantDTO;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.mapper.RestaurantMapper;
import com.mathdev.quickbite.repositories.ProductRepository;
import com.mathdev.quickbite.repositories.RestaurantRepository;
import com.mathdev.quickbite.services.exceptions.DatabaseException;
import com.mathdev.quickbite.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository repo;

	@Autowired
	ProductRepository productRepository;

	// GET SERVICES
	public List<RestaurantDTO> findAll() {
		try {
			List<Restaurant> restaurants = repo.findAll();

			return restaurants.stream().map(RestaurantMapper::toDTO).collect(Collectors.toList()); // fitful for Java <
																									// 16 versions
		} catch (Exception e) {
			throw new DatabaseException("Error retrieving restaurants: " + e.getMessage());
		}
	}

	public RestaurantDTO findById(Long id) {
		Restaurant obj = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant with id " + id + " not found!"));

		return RestaurantMapper.toDTO(obj);
	}

	public List<ProductDTO> findProductsByRestaurant(Long restaurantId) {
		if (!repo.existsById(restaurantId)) {
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}

		List<Product> products = productRepository.findByRestaurantId(restaurantId);

		return products.stream().map(product -> new ProductDTO(product.getId(), product.getName(),
				product.getDescription(), product.getBasePrice(), product.getRestaurant().getId()))
				.collect(Collectors.toList());
	}

	// POST SERVICES
	public RestaurantDTO insert(RestaurantDTO dto) {
		try {
			Restaurant obj = RestaurantMapper.fromDTOInsert(dto);

			obj = repo.save(obj);

			return RestaurantMapper.toDTO(obj);
		} catch (Exception e) {
			throw new DatabaseException("Error saving product: " + e.getMessage());
		}
	}

	// DELETE SERVICES
	public void deleteUser(Long id) {
		if (!repo.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	// PUT SERVICES
	public RestaurantDTO update(Long id, RestaurantDTO newData) {
	    try {
	        Restaurant updatedRestaurant = RestaurantMapper.fromDTOUpdate(
	            new RestaurantDTO(id, newData.name(), newData.email(), newData.address()),
	            repo
	        );

	        updatedRestaurant = repo.save(updatedRestaurant);
	        return RestaurantMapper.toDTO(updatedRestaurant);

	    } catch (EntityNotFoundException e) {
	        throw new ResourceNotFoundException(id);
	    }
	}



}
