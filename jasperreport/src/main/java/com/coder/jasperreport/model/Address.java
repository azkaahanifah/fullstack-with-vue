package com.coder.jasperreport.model;

import lombok.Data;

@Data
public class Address {
	
	private String firstName;
	private String lastName;
	private String streetName;
	private String postalCode;
	private String town;
	private String country;
	
	public Address(String firtName, String lastName, String streetName, String postalCode, String town, String country) {
		this.firstName = firtName;
		this.lastName = lastName;
		this.streetName = streetName;
		this.postalCode = postalCode;
		this.town = town;
		this.country = country;
	}

}
