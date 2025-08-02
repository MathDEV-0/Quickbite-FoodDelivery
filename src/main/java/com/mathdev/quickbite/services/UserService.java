package com.mathdev.quickbite.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	//GET SERVICES
	public List<User> findAll(){
		return repo.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repo.findById(id);
		
		return obj.get();
	}
	
	
	//POST SERVICES
	public User createUser(User user) {
		return repo.save(user);
	}
	
	//DELETE SERVICES
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
	
	//PUT SERVICES
	public User update(Long id, User newUser) {
		User entity =  repo.getReferenceById(id);
		updateData(entity, newUser);
		
		return repo.save(entity);
	}
	
	private void updateData(User entity, User newUser) {
		entity.setName(newUser.getName());
		entity.setEmail(newUser.getEmail());
		entity.setAdress(newUser.getAdress());
	}
}
