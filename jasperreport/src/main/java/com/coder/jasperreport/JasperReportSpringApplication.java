package com.coder.jasperreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coder.jasperreport"})
public class JasperReportSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JasperReportSpringApplication.class, args);
	}

}
