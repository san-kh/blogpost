package com.springboot.blog.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtopaylod.LoginDto;
import com.springboot.blog.dtopaylod.SignUpDto;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager; 
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	//inject the jwt token generator class JwtTokenProvider
	private JwtTokenProvider jwtTokenProvider;
	
	
	

	public AuthServiceImpl(AuthenticationManager authenticationManager, 
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider=jwtTokenProvider; 
	}

	@Override
	public String login(LoginDto loginDto) {
		// TODO Auto-generated method stub
	Authentication  authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(), 
				loginDto.getPassword())); 
		//need to pass authentication object to spring security context
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	//generate the token on authentication  & return 
	String token=jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

	@Override
	public String signUp(SignUpDto signUpDto) {
		// TODO Auto-generated method stub
		//check for username exists in DB
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}
		//check for email exists in DB
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}
		//if niether username or email exists in db then map signUpDto to user
		User user=new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		
		Set<Role> roles=new HashSet<Role>(); 
		Role userRole= roleRepository.findByName("ROLE_USER").get(); 
		roles.add(userRole);
		//pass this roles set to setRoles of  user entity 
		user.setRoles(roles);
		
		//save this user entity
		userRepository.save(user);
		
		return "User Sign-Up sucessfully!.";
	} 
	

}
