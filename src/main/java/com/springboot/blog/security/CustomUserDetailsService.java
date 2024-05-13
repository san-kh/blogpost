package com.springboot.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository; 
	

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user= userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
		.orElseThrow(()-> new UsernameNotFoundException("User not found with user or email: "+usernameOrEmail));
		
		//convert this user object into spring security user object
         //user object has set of roles to mapped with set of granted authorities
		  Set<GrantedAuthority> autorities= user.getRoles()
		    .stream()
		    .map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()); 
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), autorities); 
		 // return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), autorities);
	}

}
