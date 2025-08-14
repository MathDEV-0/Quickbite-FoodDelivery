package com.mathdev.quickbite.mapper;

import com.mathdev.quickbite.dto.ProductDTO;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.repositories.RestaurantRepository;

public class ProductMapper {

	public static Product fromDTO(ProductDTO dto, RestaurantRepository restaurantRepository) {
		Restaurant restaurant = restaurantRepository.findById(dto.restaurantId())
				.orElseThrow(() -> new RuntimeException("Restaurant not found"));

		return new Product(dto.id(), dto.name(), dto.description(), dto.basePrice(), restaurant);
	}

	public static ProductDTO toDTO(Product entity) {
		return new ProductDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getBasePrice(),
				entity.getRestaurant().getId());
	}
}
