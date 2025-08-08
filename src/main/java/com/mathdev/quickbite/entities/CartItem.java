package com.mathdev.quickbite.entities;

import java.io.Serializable;
import java.util.Objects;

import com.mathdev.quickbite.entities.pk.CartItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CartItemPK id = new CartItemPK();
	private Integer quantity;
	private Double price;

	public CartItem() {

	}

	public CartItem(Cart cart, Product product, Integer quantity, Double price) {
		super();
		this.id.setCart(cart);
		this.id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}

	public CartItemPK getId() {
		return id;
	}

	public void setId(CartItemPK id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSubtotal() {
		return price * quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(id, other.id);
	}

}
