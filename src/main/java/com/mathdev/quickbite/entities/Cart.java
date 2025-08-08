package com.mathdev.quickbite.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mathdev.quickbite.entities.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cart")
public class Cart implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartItem> items = new ArrayList<CartItem>();
	
	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	
	public Cart() {
		
	}

	public Cart(Long id, User user) {
		super();
		this.id = id;
		this.user = user;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}
	
	public void addItem(CartItem item) {
        items.add(item);
        item.getId().setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.getId().setCart(null);
    }


	public void setUser(User user) {
		this.user = user;
	}


	public List<CartItem> getItems() {
		return items;
	}
	
	public Double getSubtotal() {
		double sum = 0.0;
		
		for(CartItem item: items) {
			sum += item.getSubtotal();
		}
		
		return sum;
	}
	
	public Double getTotal() {
		Double total = getSubtotal();
		if(this.coupon != null && this.coupon.getActive()) {
			total = total	- (total *(coupon.getDiscount()/100));
		}
		return total;
	}
	
	public void addProduct(Product product, int quantity) {
	    for (CartItem item : items) {
	        if (item.getId().getProduct().equals(product)) {
	            item.setQuantity(item.getQuantity() + quantity);
	            return;
	        }
	    }
	    items.add(new CartItem(this, product, quantity, product.getBasePrice()));
	}
	
	public void applyCoupon(Coupon coupon) {
	    if (coupon.getActive()) {
	        this.coupon = coupon;
	    }
	}
	
	public Order checkout(User user) {
		Order order = new Order(null, Instant.now(),user,OrderStatus.PENDING);
		if (this.coupon != null && this.coupon.getActive()) {
	        order.setCoupon(this.coupon);
	    }
	    for (CartItem item : this.items) {
	        OrderItem orderItem = new OrderItem(order, item.getId().getProduct(), item.getQuantity(), item.getPrice());
	        order.getItems().add(orderItem);
	    }
	    this.items.clear();
	    this.coupon = null;
	    
	    return order;
	}
	
}
