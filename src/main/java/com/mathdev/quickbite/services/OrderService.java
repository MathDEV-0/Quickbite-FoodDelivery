package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.OrderDTO;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.mapper.OrderMapper;
import com.mathdev.quickbite.repositories.OrderRepository;
import com.mathdev.quickbite.repositories.UserRepository;
import com.mathdev.quickbite.services.exceptions.DatabaseException;
import com.mathdev.quickbite.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import com.mathdev.quickbite.entities.Order;

@Service
public class OrderService {

	@Autowired
	OrderRepository repo;

	@Autowired
	UserRepository userRepository;

	// GET SERVICES
	public List<OrderDTO> findAll() {
		try {
			List<Order> orders = repo.findAll();

			return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList()); // fitful for Java < 16
																							// versions
		} catch (Exception e) {
			throw new DatabaseException("Error retrieving products: " + e.getMessage());
		}
	}

	public OrderDTO findById(Long id) {
		Order obj = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order with id" + id + " not found!"));

		return OrderMapper.toDTO(obj);
	}

	public List<OrderDTO> findOrdersByUserId(Long userId) {
		try {
			List<Order> list = repo.findByClientId(userId);
			return list.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
		} catch (Exception e) {
			throw new DatabaseException("Error retrieving orders by user: " + e.getMessage());
		}
	}

	// POST SERVICES
	public OrderDTO insert(OrderDTO dto) {
		try {
			Order obj = OrderMapper.fromDTO(dto);

			obj = repo.save(obj);

			return OrderMapper.toDTO(obj);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	// DELETE SERVICES
	public void deleteOrder(Long id) {
		if (!repo.existsById(id)) {
			throw new ResourceNotFoundException("Order id " + id + " does not exist");
		}
		try {
			repo.deleteById(id);

		} catch (Exception e) {
			throw new DatabaseException("Error deleting order: " + e.getMessage());
		}
	}

	// PUT SERVICES
	public OrderDTO update(Long id, OrderDTO newData) {
		try {
			Order entity = repo.getReferenceById(id);
			updateData(entity, newData);

			return OrderMapper.toDTO(repo.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	// AUXILIARY METHODS
	private void updateData(Order entity, OrderDTO newData) {
		entity.setMoment(newData.moment());
		if (newData.client() != null) {
			User user = userRepository.findById(newData.client().id())
					.orElseThrow(() -> new ResourceNotFoundException("User from this order not found"));
			entity.setClient(user);
		}
	}

}
