package com.coder.springemail.service;

import com.coder.springemail.dto.EmailRequest;

public interface EmailService {
	
	public void sendEmail(EmailRequest emailRequest);

}
