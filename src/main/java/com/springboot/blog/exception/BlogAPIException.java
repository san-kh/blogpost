package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BlogAPIException extends RuntimeException{
	private HttpStatus status; 
    private String message;
    
	public BlogAPIException(String message, HttpStatus status, String message2) {
		super(message);
		this.status = status;
		message = message2;
	}

	public BlogAPIException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	} 
    
    
    
	

}
