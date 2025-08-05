package com.mathdev.quickbite.dto;

public record OrderItemDTO(
    Long productId,
    String productName,
    Double price,
    Integer quantity,
    Double subtotal
) {}
