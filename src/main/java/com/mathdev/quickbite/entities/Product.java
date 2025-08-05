package com.mathdev.quickbite.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private Double basePrice; 
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@OneToMany(mappedBy = "id.product")
	private Set<OrderItem> orders = new HashSet<>();
	
	public Product() {
		
	}

	public Product(Long id, String name, String description, Double basePrice, Restaurant restaurant) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.basePrice = basePrice;
		this.restaurant = restaurant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	@JsonIgnore
	public Set<Order> getOrders(){
		Set<Order> temp = new HashSet<>();
		
		for(OrderItem x: orders) {
			temp.add(x.getOrder());
		}
		
		return temp;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
