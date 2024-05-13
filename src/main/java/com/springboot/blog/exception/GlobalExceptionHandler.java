package com.springboot.blog.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.dtopaylod.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler  {
	//specific exception handler
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
			WebRequest webRequest){
		ErrorDetails errorDetails=new ErrorDetails(new Date(), resourceNotFoundException.getMessage(), webRequest.getDescription(false));
		
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException blogAPIException,
			WebRequest webRequest){
       ErrorDetails errorDetails=new ErrorDetails(new Date(), blogAPIException.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST); 
		
	}
	//Global Exception handler
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
			WebRequest webRequest){
       ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.MULTI_STATUS); 
		
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object>  handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
			WebRequest webRequest) {
		Map<String,String > errors=new HashMap<String, String>();
		exception.getBindingResult().getAllErrors().forEach((i)->{
			String fieldName=((FieldError)i).getField();
			String message=i.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST); 
	}
	
}
