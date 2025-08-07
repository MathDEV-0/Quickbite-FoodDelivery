package com.mathdev.quickbite.entities.enums;


public enum OrderStatus {
	PENDING(1),
	PREPARING(2),
	OUT_FOR_DELIVERY(3),
	DELIVERED(4),
	CANCELED(5);
	
private int code;
	
	private OrderStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static OrderStatus valueOf(int code) {
		for(OrderStatus value:  OrderStatus.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid OrderStatus code");
	}
}
