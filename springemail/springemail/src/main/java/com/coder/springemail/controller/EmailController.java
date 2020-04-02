package com.coder.springemail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.springemail.dto.EmailRequest;
import com.coder.springemail.service.EmailService;

@RestController
@RequestMapping(value = "/api")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(value = "/sendEmail")
	public String sendEmail(@RequestBody EmailRequest emailRequest) {
		emailService.sendEmail(emailRequest);
		return "Success!";
	}

}
