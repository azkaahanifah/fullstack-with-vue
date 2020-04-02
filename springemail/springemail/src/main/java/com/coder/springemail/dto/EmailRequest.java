package com.coder.springemail.dto;

import java.util.List;

import lombok.Data;

@Data
public class EmailRequest {
	
	private String toIds;
	private String ccIds;
	private String subject;
	private String content;
	
	private String contentType;
	private List<Object> attachments;
	
	public EmailRequest() {
		contentType = "text/plain";
	}
	
}
