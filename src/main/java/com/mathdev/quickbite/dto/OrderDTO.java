package com.mathdev.quickbite.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record OrderDTO(
		Long id,
		Instant moment,
		@JsonIgnore
		UserDTO client) {

}
