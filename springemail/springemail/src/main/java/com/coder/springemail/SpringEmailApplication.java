package com.coder.springemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coder.springemail"})
public class SpringEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEmailApplication.class, args);
	}

}
