package com.coder.springemail.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.coder.springemail.constant.EmailConstants;

@Configuration
public class EmailConfiguration {
	
	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(env.getProperty(EmailConstants.MAIL_HOST));
		mailSender.setPort(Integer.valueOf(env.getProperty(EmailConstants.MAIL_PORT)));
		mailSender.setUsername(env.getProperty(EmailConstants.MAIL_USERNAME));
		mailSender.setPassword(env.getProperty(EmailConstants.MAIL_PASSWORD));
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");
		
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

}
