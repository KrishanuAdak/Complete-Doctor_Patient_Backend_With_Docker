package com.example.demo1.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUser extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3829405201764333539L;
	public String message;

	public UnauthorizedUser(String message) {
		super(message);
		this.message = message; 
	}
	
	
	

}
