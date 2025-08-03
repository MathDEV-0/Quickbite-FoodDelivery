package com.mathdev.quickbite.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.mathdev.quickbite.entities.Order;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.entities.enums.OrderStatus;
import com.mathdev.quickbite.repositories.OrderRepository;
import com.mathdev.quickbite.repositories.ProductRepository;
import com.mathdev.quickbite.repositories.RestaurantRepository;
import com.mathdev.quickbite.repositories.UserRepository;

@Configuration
public class TestConfig implements CommandLineRunner{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public void run(String... args) throws Exception {
	    System.out.println("Seeding...");
	    
	    User u1 = new User(null,"Carlos da Silva", "carlos@gmail.com", "123","Travessa do Tranco");
	    User u2 = new User(null,"Maria de Jesus", "maria@gmail.com", "123", "Beco Diagonal");
	    
	    userRepository.saveAll(Arrays.asList(u1,u2));
	    
	    Restaurant r1 = new Restaurant(null,"Caldeirão Furado","caldeiraofurado@hogwarts.com","1235","Beco Diagonal");
	    Restaurant r2 = new Restaurant(null,"Três Vassouras","tresvassouras@hogwarts.com","1235","Hogsmead");
	    
	    restaurantRepository.saveAll(Arrays.asList(r1,r2));
	    
	    Product p1 = new Product(null, "Cerveja Amanteigada", "Deliciosa cerveja amanteigada servida quente", 10.0, r1);
	    Product p2 = new Product(null, "Sapos de Chocolate", "Doces em formato de sapo que pulam!", 5.0, r2);
	    Product p3 = new Product(null, "Poção Polissuco", "Permite assumir a forma de outra pessoa", 50.0, r2);
	    
	    r1.getProducts().add(p1);
	    r2.getProducts().add(p2);
	    r2.getProducts().add(p3);
	    
	    productRepository.saveAll(Arrays.asList(p1,p2,p3));
	    
	    Order o1 = new Order(null, Instant.now(), u1);
        Order o2 = new Order(null, Instant.now(), u2);

     

        orderRepository.saveAll(Arrays.asList(o1, o2));
	}

	
	
}
