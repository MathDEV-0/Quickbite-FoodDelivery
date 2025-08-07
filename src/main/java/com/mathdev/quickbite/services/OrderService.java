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
import com.mathdev.quickbite.entities.Order;

@Service
public class OrderService {

	@Autowired
	OrderRepository repo;

	@Autowired
	UserRepository userRepository;

	// GET SERVICES
	public List<OrderDTO> findAll() {
		List<Order> orders = repo.findAll();

		return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList()); // fitful for Java < 16 versions
	}

	public OrderDTO findById(Long id) {
		Order obj = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));

		return OrderMapper.toDTO(obj);
	}

	public List<OrderDTO> findOrdersByUserId(Long userId) {
		List<Order> list = repo.findByClientId(userId);
		return list.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}

	// POST SERVICES
	public OrderDTO insert(OrderDTO dto) {
		Order obj = OrderMapper.fromDTO(dto);
		obj = repo.save(obj);

		return OrderMapper.toDTO(obj);
	}

	// DELETE SERVICES
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}

	// PUT SERVICES
	public OrderDTO update(Long id, OrderDTO newData) {
		Order entity = repo.getReferenceById(id);
		updateData(entity, newData);

		return OrderMapper.toDTO(repo.save(entity));
	}

	// AUXILIARY METHODS
	private void updateData(Order entity, OrderDTO newData) {
		entity.setMoment(newData.moment());
		if (newData.client() != null) {
			User user = userRepository.findById(newData.client().id())
					.orElseThrow(() -> new RuntimeException("User from this order not found"));
			entity.setClient(user);
		}
	}

}

