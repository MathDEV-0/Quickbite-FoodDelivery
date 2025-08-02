package com.mathdev.quickbite.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.repositories.RestaurantRepository;
import com.mathdev.quickbite.repositories.UserRepository;

@Configuration
public class TestConfig implements CommandLineRunner{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Seeding...");
		
		User u1 = new User(null,"Carlos da Silva", "carlos@gmail.com", "123","Travessa do Tranco");
		User u2 = new User(null,"Maria de Jesus", "maria@gmail.com", "123", "Beco Diagonal");
		
		Restaurant r1 = new Restaurant(null,"Caldeirão Furado","Beco Diagonal");
		
		
		userRepository.saveAll(Arrays.asList(u1,u2));
		restaurantRepository.saveAll(Arrays.asList(r1));
	}
	
	
}
