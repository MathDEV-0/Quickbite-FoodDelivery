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

import com.mathdev.quickbite.dto.OrderDTO;
import com.mathdev.quickbite.dto.UserDTO;
import com.mathdev.quickbite.dto.UserInsertDTO;
import com.mathdev.quickbite.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	UserService userService;

	// GET CONTROLLERS
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> temp = userService.findAll();

		return ResponseEntity.ok().body(temp);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		UserDTO obj = userService.findById(id);

		return ResponseEntity.ok().body(obj);
	}

	@GetMapping("/{id}/orders")
	public ResponseEntity<List<OrderDTO>> getOrdersFromUser(@PathVariable Long id) {
		if (userService.findById(id) == null) {
			throw new RuntimeException();
		}

		List<OrderDTO> orders = userService.getOrdersByUser(id);
		return ResponseEntity.ok(orders);
	}

	// POST CONTROLLERS
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserInsertDTO dto) {
		UserDTO tempDTO = userService.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tempDTO.id()).toUri();

		return ResponseEntity.created(uri).body(tempDTO);
	}

	// DELETE CONTROLLERS
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);

		return ResponseEntity.noContent().build();
	}

	// PUT CONTROLLERS
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
		dto = userService.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

}
