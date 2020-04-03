package com.coder.jasperreport.model;

import java.util.List;

import lombok.Data;

@Data
public class Order {
	
	private String code;
	private Address address;
	private List<OrderEntry> entries;
	
	public Order(String code, Address address, List<OrderEntry> entries) {
		this.code = code;
		this.address = address;
		this.entries = entries;
	}
	
	public Double getTotalPrice() {
        return getEntries().stream().mapToDouble(OrderEntry::getPriceTotal).sum();
    }

    public Integer getTotalQuantity() {
        return getEntries().stream().mapToInt(OrderEntry::getQuantity).sum();
    }

}
