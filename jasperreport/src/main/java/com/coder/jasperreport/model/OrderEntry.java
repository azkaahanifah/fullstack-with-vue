package com.coder.jasperreport.model;

import lombok.Data;

@Data
public class OrderEntry {
	
	private String productName;
	private Integer quantity;
	private Double price;
	
	public OrderEntry(String productName, Integer quantity, Double price) {
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	
	public Double getPriceTotal() {
        return quantity * price;
    }

}
