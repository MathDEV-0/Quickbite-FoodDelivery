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

import com.mathdev.quickbite.dto.ProductDTO;
import com.mathdev.quickbite.services.ProductService;


@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	ProductService userService;
	
	//GET CONTROLLERS
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> temp = userService.findAll();
		
		return ResponseEntity.ok().body(temp);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
		ProductDTO obj = userService.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	//POST CONTROLLERS
	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto){
		ProductDTO tempDTO = userService.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.id()).toUri();
		
		return ResponseEntity.created(uri).body(tempDTO);
	}
	
	//DELETE CONTROLLERS
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		userService.deleteUser(id);
		
		return ResponseEntity.noContent().build();
	}
	
	//PUT CONTROLLERS
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@RequestBody ProductDTO dto){
		dto = userService.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
}
