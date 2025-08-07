package com.mathdev.quickbite.dto;

import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathdev.quickbite.entities.enums.OrderStatus;

public record OrderDTO(
		Long id,
		Instant moment,
		@JsonIgnore
		UserDTO client,
		Set<OrderItemDTO> products,
		Double total,
		OrderStatus status){

}
