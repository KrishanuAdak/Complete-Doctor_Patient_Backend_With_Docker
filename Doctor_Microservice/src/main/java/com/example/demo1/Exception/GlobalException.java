package com.example.demo1.Exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceException(ResourceNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(UnauthorizedUser.class)
	public ResponseEntity<?> unauthorizedUser(UnauthorizedUser ex)
	{
		return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "You dont have permission", "details", ex.getMessage()));
		
	}
	
	
	
	
	

}
