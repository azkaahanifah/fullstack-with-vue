package com.coder.jasperreport.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coder.jasperreport.model.Address;
import com.coder.jasperreport.model.Order;
import com.coder.jasperreport.model.OrderEntry;

@Service
public class MockOrderService {
	
	public Order getOrderByCode(final String code) {
		return order(code);
	}
	
	private Order order(String code) {
		return new Order(code, address(), entries());
	}
	
	private Address address() {
		return new Address("Azka",
				"Hanifah",
				"Cibubur Country",
				"16961",
				"Kab Bogor",
				"Bogor");
	}
	
	private List<OrderEntry> entries() {
		return new ArrayList<OrderEntry>() {
			{
				add(new OrderEntry("iPhone 11 Pro Max", 1, 500d));
				add(new OrderEntry("Asus TUF Gaming", 1, 450d));
			}
		};
	}

}
