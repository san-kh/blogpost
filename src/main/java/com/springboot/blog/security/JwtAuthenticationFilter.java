package com.springboot.blog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//configure this filter in spring securities (SecurityConfig) before any filter
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private JwtTokenProvider jwtTokenProvider; 
	private UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
			throws ServletException, IOException {
	
		//get JWT token from http request header
		//Authorization Bearer
		String token=getTokenFromRequest(request);
		
		//validate Token for null or empty
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
			//get the username from token
			String username=jwtTokenProvider.getUsername(token);
			
			//load the user associated with token
		UserDetails userDetails=userDetailsService.loadUserByUsername(username);
		
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		
		//add request object to this authenticationToken
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		//set this authenticationToken to the security contextholder
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
	}
		//call dofilter chain method
		filterChain.doFilter(request, response); 
	}
	
	private String getTokenFromRequest(HttpServletRequest request) { 
	
		 String bearerToken=request.getHeader("Authorization"); 
		 //String JWTToken= bearerToken.substring(7, bearerToken.length());  
		 
		 if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")) { 
			 return bearerToken.substring(7, bearerToken.length());
		 }
		
		return null; 
		
	}

}
