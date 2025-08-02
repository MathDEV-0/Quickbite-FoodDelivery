package com.mathdev.quickbite.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	UserService userService;
	
	//GET CONTROLLERS
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> temp = userService.findAll();
		
		return ResponseEntity.ok().body(temp);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User obj = userService.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	//POST CONTROLLERS
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User obj){
		obj = userService.createUser(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	//DELETE CONTROLLERS
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		
		return ResponseEntity.noContent().build();
	}
	
	//PUT CONTROLLERS
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User obj){
		obj = userService.update(id, obj);
		
		return ResponseEntity.ok().body(obj);
	}
	
}
