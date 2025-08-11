package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.TransientObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.OrderDTO;
import com.mathdev.quickbite.dto.UserDTO;
import com.mathdev.quickbite.dto.UserInsertDTO;
import com.mathdev.quickbite.entities.Order;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.mapper.OrderMapper;
import com.mathdev.quickbite.mapper.UserMapper;
import com.mathdev.quickbite.repositories.OrderRepository;
import com.mathdev.quickbite.repositories.UserRepository;
import com.mathdev.quickbite.services.exceptions.DatabaseException;
import com.mathdev.quickbite.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository repo;

	@Autowired
	OrderRepository orderRepository;

	// GET SERVICES
	public List<UserDTO> findAll() {
		try {
			List<User> users = repo.findAll();

			return users.stream().map(UserMapper::toDTO).toList();
		} catch (Exception e) {
			throw new DatabaseException("Error retrieving users: " + e.getMessage());
		}
	}

	public UserDTO findById(Long id) {

		User obj = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));

		return UserMapper.toDTO(obj);

	}

	public List<OrderDTO> getOrdersByUser(Long userId) {
		List<Order> orders = orderRepository.findByClientId(userId);
		return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}

	// POST SERVICES
	public UserDTO insert(UserInsertDTO dto) {
		try {
			User obj = UserMapper.fromDTOInsert(dto);

			obj = repo.save(obj);

			return UserMapper.toDTO(obj);
		} catch (Exception e) {
			throw new DatabaseException("Error saving product: " + e.getMessage());
		}
	}

	// DELETE SERVICES
	public void deleteUser(Long id) {
		if(!repo.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	// PUT SERVICES
	public UserDTO update(Long id, UserDTO newUser) {
		User entity = repo.getReferenceById(id);
		updateData(entity, newUser);

		return UserMapper.toDTO(repo.save(entity));
	}

	// AUXILIARY METHODS
	private void updateData(User entity, UserDTO newUser) {
		entity.setName(newUser.name());
		entity.setEmail(newUser.email());
		entity.setAddress(newUser.address());
	}

}
