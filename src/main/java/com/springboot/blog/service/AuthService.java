package com.springboot.blog.service;

import com.springboot.blog.dtopaylod.LoginDto;
import com.springboot.blog.dtopaylod.SignUpDto;

public interface AuthService {
	
	String login(LoginDto loginDto);
    String signUp(SignUpDto signUpDto); 
}
