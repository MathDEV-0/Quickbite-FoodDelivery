package com.mathdev.quickbite.dto;

import java.util.List;

public record CartDTO(
		List<CartItemDTO> items, 
		Double totalValue, 
		Integer totalQuantity) {

}
