package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtopaylod.JWTAuthResponse;
import com.springboot.blog.dtopaylod.LoginDto;
import com.springboot.blog.dtopaylod.SignUpDto;
import com.springboot.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	//build login REST API
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
		
		String jwtToken = authService.login(loginDto);
		JWTAuthResponse jwtAuthResponse=new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(jwtToken);
		return ResponseEntity.ok(jwtAuthResponse); 
		}
	
	//build sign-Up REST API
	@PostMapping(value = {"/signup","/register"})
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
		String response= authService.signUp(signUpDto);
		return new ResponseEntity<String>(response, HttpStatus.CREATED); 
		
	}
}
