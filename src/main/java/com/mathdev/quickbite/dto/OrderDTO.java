package com.mathdev.quickbite.dto;

import java.time.Instant;

public record OrderDTO(
		Long id,
		Instant moment,
		UserDTO client) {

}
