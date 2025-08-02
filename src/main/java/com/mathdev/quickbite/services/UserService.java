package com.mathdev.quickbite.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.UserDTO;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	//GET SERVICES
	public List<UserDTO> findAll(){
		List<User> users = repo.findAll();
		
		return users.stream().map(this::toDTO).toList();
	}
	
	public UserDTO findById(Long id) {
		User obj = repo.findById(id).orElseThrow(()->new RuntimeException("User not found!"));
		
		return toDTO(obj);
	}
	
	
	//POST SERVICES
	public UserDTO insert(UserDTO dto) {
		User obj = fromDTO(dto);
		obj = repo.save(obj);
		
		return toDTO(obj);
	}
	
	//DELETE SERVICES
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
	
	//PUT SERVICES
	public UserDTO update(Long id, UserDTO newUser) {
		User entity =  repo.getReferenceById(id);
		updateData(entity, newUser);
		
		return toDTO(repo.save(entity));
	}
	
	//AUXILIARY METHODS
	private void updateData(User entity, UserDTO newUser) {
		entity.setName(newUser.name());
		entity.setEmail(newUser.email());
		entity.setAddress(newUser.address());
	}
	
	private User fromDTO(UserDTO dto) {
		return new User(dto.id(),dto.name(),dto.email(),null,dto.address());
	}
	
	private UserDTO toDTO(User entity) {
		return new UserDTO(entity.getId(),entity.getName(),entity.getEmail(),entity.getAddress());
	}
}
