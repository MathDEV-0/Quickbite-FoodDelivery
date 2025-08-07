package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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


@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	OrderRepository orderRepository;
	
	//GET SERVICES
	public List<UserDTO> findAll(){
		List<User> users = repo.findAll();
		
		return users.stream().map(UserMapper::toDTO).toList();
	}
	
	public UserDTO findById(Long id) {
		User obj = repo.findById(id).orElseThrow(()->new RuntimeException("User not found!"));
		
		return UserMapper.toDTO(obj);
	}
	
	public List<OrderDTO> getOrdersByUser(Long userId) {
	    List<Order> orders = orderRepository.findByClientId(userId);
	    return orders.stream()
	                 .map(OrderMapper::toDTO)
	                 .collect(Collectors.toList());
	}

	//POST SERVICES
	public UserDTO insert(UserInsertDTO dto) {
		User obj = UserMapper.fromDTOInsert(dto);
		obj = repo.save(obj);
		
		return UserMapper.toDTO(obj);
	}
	
	//DELETE SERVICES
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
	
	//PUT SERVICES
	public UserDTO update(Long id, UserDTO newUser) {
		User entity =  repo.getReferenceById(id);
		updateData(entity, newUser);
		
		return UserMapper.toDTO(repo.save(entity));
	}
	
	//AUXILIARY METHODS
	private void updateData(User entity, UserDTO newUser) {
		entity.setName(newUser.name());
		entity.setEmail(newUser.email());
		entity.setAddress(newUser.address());
	}
	

}
