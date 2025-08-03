package com.mathdev.quickbite.dto;

public record ProductDTO(
		Long id,
		String name,
		String description,
		Double basePrice,
		Long restaurantId) {

}
