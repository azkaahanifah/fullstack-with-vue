package com.coder.springemail.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.coder.springemail.constant.EmailConstants;
import com.coder.springemail.dto.EmailRequest;
import com.coder.springemail.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	private Environment env;

	@Override
	public void sendEmail(EmailRequest emailRequest) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			
			mimeMessageHelper.setFrom(env.getProperty(EmailConstants.EMAIL_FROM_ID));
			mimeMessageHelper.setTo(emailRequest.getToIds());
			mimeMessageHelper.setCc(emailRequest.getCcIds());
			mimeMessageHelper.setSubject(emailRequest.getSubject());
			mimeMessageHelper.setText(emailRequest.getContent());
			
			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
