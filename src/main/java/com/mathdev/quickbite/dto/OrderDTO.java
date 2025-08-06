package com.mathdev.quickbite.dto;

import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record OrderDTO(
		Long id,
		Instant moment,
		@JsonIgnore
		UserDTO client,
		Set<OrderItemDTO> products,
		Double total){

}
