package com.mathdev.quickbite.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.mathdev.quickbite.dto.OrderDTO;
import com.mathdev.quickbite.dto.OrderItemDTO;
import com.mathdev.quickbite.dto.UserDTO;
import com.mathdev.quickbite.entities.Order;
import com.mathdev.quickbite.entities.User;

public class OrderMapper {

	public static OrderDTO toDTO(Order entity) {
		UserDTO userDto = null;
		if (entity.getClient() != null) {
			userDto = new UserDTO(entity.getClient().getId(), entity.getClient().getName(),
					entity.getClient().getEmail(), entity.getClient().getAddress());
		}
		Set<OrderItemDTO> itemsDTO = entity
				.getItems().stream().map(item -> new OrderItemDTO(item.getProduct().getId(),
						item.getProduct().getName(), item.getPrice(), item.getQuantity(), item.getSubtotal()))
				.collect(Collectors.toSet());

		double total = 0.0;

		for (OrderItemDTO item : itemsDTO) {
			total += item.subtotal();
		}
		if (entity.getCoupon() != null) {
			total = total * (1 - entity.getCoupon().getDiscount() / 100);
			total = Math.round(total * 100) / 100.0;
		}

		return new OrderDTO(entity.getId(), entity.getMoment(), userDto, itemsDTO, total, entity.getOrderStatus());
	}

    public static Order fromDTO(OrderDTO dto) {
		User user = null;
		if (dto.client() != null) {
			user = new User();
			user.setId(dto.client().id());
		}
		return new Order(dto.id(), dto.moment(), user, dto.status());
	}
}
