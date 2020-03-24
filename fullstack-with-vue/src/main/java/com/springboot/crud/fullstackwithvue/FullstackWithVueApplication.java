package com.springboot.crud.fullstackwithvue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.springboot.crud.fullstackwithvue"})
public class FullstackWithVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullstackWithVueApplication.class, args);
	}

}
