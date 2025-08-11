package com.mathdev.quickbite.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.ProductDTO;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.repositories.ProductRepository;
import com.mathdev.quickbite.repositories.RestaurantRepository;
import com.mathdev.quickbite.services.exceptions.DatabaseException;
import com.mathdev.quickbite.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	ProductRepository repo;

	@Autowired
	private RestaurantRepository restaurantRepository;

	// GET SERVICES
	public List<ProductDTO> findAll() {
		try {
			List<Product> products = repo.findAll();

			return products.stream().map(this::toDTO).toList();
		} catch (Exception e) {
			throw new DatabaseException("Error retrieving products: " + e.getMessage());
		}
	}

	public ProductDTO findById(Long id) {
		Product obj = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found!"));

		return toDTO(obj);
	}

	// POST SERVICES
	public ProductDTO insert(ProductDTO dto) {
		try{
			Product obj = fromDTO(dto);
		
		obj = repo.save(obj);

		return toDTO(obj);
		}catch (Exception e) {
            throw new DatabaseException("Error saving product: " + e.getMessage());
        }
	}

	// DELETE SERVICES
	public void deleteProduct(Long id) {
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
	public ProductDTO update(Long id, ProductDTO newUser) {
		try{
			Product entity = repo.getReferenceById(id);
		
		
		updateData(entity, newUser);

		return toDTO(repo.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	// AUXILIARY METHODS
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
		return new ProductDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getBasePrice(),
				entity.getRestaurant().getId());
	}
}
