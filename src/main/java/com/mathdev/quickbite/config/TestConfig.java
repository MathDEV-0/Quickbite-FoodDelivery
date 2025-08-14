package com.mathdev.quickbite.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.mathdev.quickbite.entities.Cart;
import com.mathdev.quickbite.entities.CartItem;
import com.mathdev.quickbite.entities.Coupon;
import com.mathdev.quickbite.entities.Order;
import com.mathdev.quickbite.entities.OrderItem;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.Restaurant;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.entities.enums.OrderStatus;
import com.mathdev.quickbite.repositories.CartItemRepository;
import com.mathdev.quickbite.repositories.CartRepository;
import com.mathdev.quickbite.repositories.CouponRepository;
import com.mathdev.quickbite.repositories.OrderItemRepository;
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
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	
	@Override
	public void run(String... args) throws Exception {
		cartItemRepository.deleteAll();
	    orderItemRepository.deleteAll();
	    orderRepository.deleteAll();
	    cartRepository.deleteAll();
	    couponRepository.deleteAll();
	    productRepository.deleteAll();
	    restaurantRepository.deleteAll();
	    userRepository.deleteAll();
	    
		System.out.println("Seeding...");

	    User u1 = new User(null, "Carlos da Silva", "carlos@gmail.com", "123", "Travessa do Tranco");
	    User u2 = new User(null, "Maria de Jesus", "maria@gmail.com", "123", "Beco Diagonal");
	    userRepository.saveAll(Arrays.asList(u1, u2));

	    Cart cart1 = new Cart(null, u1);
	    Cart cart2 = new Cart(null, u2);
	    cartRepository.saveAll(Arrays.asList(cart1, cart2));

	    u1.setCart(cart1);
	    u2.setCart(cart2);
	    userRepository.saveAll(Arrays.asList(u1, u2));

	    Restaurant r1 = new Restaurant(null,"Caldeirão Furado","caldeiraofurado@hogwarts.com","123435","Beco Diagonal");
	    Restaurant r2 = new Restaurant(null,"Três Vassouras","tresvassouras@hogwarts.com","123335","Hogsmeade");
	    Restaurant r3 = new Restaurant(null, "Zonko's Doces & Travessuras", "zonkos@hogwarts.com", "1232315", "Hogsmeade");
	    Restaurant r4 = new Restaurant(null, "Florean Fortescue Sorveteria", "florean@hogwarts.com", "1235675", "Beco Diagonal");
	    restaurantRepository.saveAll(Arrays.asList(r1,r2,r3,r4));

	    Coupon cp1 = new Coupon(null,"HOGWARTS10",10.0,true, Instant.parse("2025-08-09T14:00:00Z"));
	    couponRepository.save(cp1);

	    Product p1 = new Product(null, "Cerveja Amanteigada", "Deliciosa cerveja amanteigada servida quente", 10.0, r1);
	    Product p2 = new Product(null, "Sapos de Chocolate", "Doces em formato de sapo que pulam!", 5.0, r2);
	    Product p3 = new Product(null, "Poção Polissuco", "Permite assumir a forma de outra pessoa", 50.0, r2);
	    Product p4 = new Product(null, "Feijõezinhos de Todos os Sabores", "Doces mágicos com sabores imprevisíveis", 4.0, r2);
	    Product p5 = new Product(null, "Varinha de Regaliz", "Doce em forma de varinha mágica", 3.5, r2);
	    Product p6 = new Product(null, "Bolo Explosivo dos Weasley", "Bolo mágico que solta faíscas ao ser cortado", 12.0, r1);
	    Product p7 = new Product(null, "Biscoitos da Sorte da Trelawney", "Biscoitos que revelam previsões místicas", 6.0, r1);
	    Product p8 = new Product(null, "Chá Enfeitiçado de Camomila", "Relaxa até o bruxo mais estressado", 7.0, r1);
	    Product p9 = new Product(null, "Rãs de Menta Mágicas", "Saltam suavemente na boca, sensação geladinha!", 4.5, r2);
	    Product p10 = new Product(null, "Pipoca Encantada de Hogwarts", "Estoura sozinha e muda de cor", 6.5, r1);
	    Product p11 = new Product(null, "Gotas de Azar", "Doces de desafio: um em cada dez causa soluços", 3.0, r2);
	    Product p12 = new Product(null, "Poção do Amor", "Adoça o humor de quem prova... ou quase!", 20.0, r2);
	    
	    productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12));
	    
	    r1.getProducts().addAll(Arrays.asList(p1, p6, p7, p8, p10));
	    r2.getProducts().addAll(Arrays.asList(p2, p3, p4, p5, p9, p11, p12));
	    
	    restaurantRepository.saveAll(Arrays.asList(r1, r2));
	    

	    CartItem ci1 = new CartItem(cart1, p1, 2, p1.getBasePrice());
	    CartItem ci2 = new CartItem(cart1, p6, 1, p6.getBasePrice());
	    CartItem ci3 = new CartItem(cart2, p2, 3, p2.getBasePrice());
	    cartItemRepository.saveAll(Arrays.asList(ci1, ci2, ci3));

	    Order o1 = new Order(null, Instant.now(), u1, OrderStatus.PENDING);
	    Order o2 = new Order(null, Instant.now(), u2, OrderStatus.OUT_FOR_DELIVERY);
	    Order o3 = new Order(null, Instant.now(), u1, OrderStatus.PREPARING);
	    o3.setCoupon(cp1);
	    orderRepository.saveAll(Arrays.asList(o1, o2, o3));

	    OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getBasePrice());
	    OrderItem oi2 = new OrderItem(o3, p3, 1, p3.getBasePrice());
	    OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getBasePrice());
	    OrderItem oi4 = new OrderItem(o1, p6, 1, p6.getBasePrice());
	    OrderItem oi5 = new OrderItem(o1, p8, 1, p8.getBasePrice());
	    OrderItem oi6 = new OrderItem(o2, p9, 3, p9.getBasePrice());
	    OrderItem oi7 = new OrderItem(o2, p11, 2, p11.getBasePrice());
	    OrderItem oi8 = new OrderItem(o3, p4, 5, p4.getBasePrice());
	    OrderItem oi9 = new OrderItem(o3, p10, 1, p10.getBasePrice());
	    OrderItem oi10 = new OrderItem(o3, p12, 1, p12.getBasePrice());
	    orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4, oi5, oi6, oi7, oi8, oi9, oi10));
	}


	
	
}
