package com.mathdev.quickbite.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.mathdev.quickbite.entities.auth.AppUser;
import com.mathdev.quickbite.entities.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_restaurants")
public class Restaurant implements Serializable, AppUser{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String address;
	
	private Role role;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet<>();
	
	public Restaurant() {
		
	}
	
	public Restaurant(Long id, String name, String email, String password, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.role = Role.RESTAURANT;
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
	
	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Set<Product> getProducts(){
		return products;
	}
	
	public void addProduct(Product product) {
	    products.add(product);
	    product.setRestaurant(this);
	}

	public void removeProduct(Product product) {
	    products.remove(product);
	    product.setRestaurant(null);
	}
	
	@Override
	public Role getRole() {
		return role;
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
		Restaurant other = (Restaurant) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
