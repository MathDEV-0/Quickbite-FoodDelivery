package com.mathdev.quickbite.dto;

import java.time.Instant;



public record CouponDTO(
	    Long id,
	    String code,
	    Double discount,
	    Instant moment,
	    Long clientId
	) {}
