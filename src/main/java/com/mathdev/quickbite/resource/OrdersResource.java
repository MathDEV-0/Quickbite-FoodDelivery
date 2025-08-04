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
import com.mathdev.quickbite.services.OrderService;


@RestController
@RequestMapping(value = "/orders")
public class OrdersResource {
	
	@Autowired
	OrderService orderService;
	

	
	//GET CONTROLLERS
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAllUsers(){
		List<OrderDTO> temp = orderService.findAll();
		
		return ResponseEntity.ok().body(temp);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> getUserById(@PathVariable Long id){
		OrderDTO obj = orderService.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}

	
	//POST CONTROLLERS
	@PostMapping
	public ResponseEntity<OrderDTO> createUser(@RequestBody OrderDTO dto){
		OrderDTO tempDTO = orderService.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.id()).toUri();
		
		return ResponseEntity.created(uri).body(tempDTO);
	}
	
	//DELETE CONTROLLERS
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		orderService.deleteUser(id);
		
		return ResponseEntity.noContent().build();
	}
	
	//PUT CONTROLLERS
	@PutMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id,@RequestBody OrderDTO dto){
		dto = orderService.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
}
