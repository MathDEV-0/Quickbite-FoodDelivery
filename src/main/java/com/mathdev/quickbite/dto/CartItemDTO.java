package com.mathdev.quickbite.dto;

public record CartItemDTO(
		Long productId,
	    String productName,
	    Integer quantity,
	    Double unitPrice,
	    Double totalPrice) {
	
}
