package com.springboot.blog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity //to enable method level security
@SecurityScheme(
		//swagger use only
		//used for adding auth header to swagger ui
		type = SecuritySchemeType.HTTP,
		name = "Bearer Authentication",
		bearerFormat = "JWT",
		scheme = "bearer"
		)
public class SecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	//this class JwtAuthenticationEntryPoint executes when unauthorized user trying to access the protected resource
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	//configure this filter in spring securities (SecurityConfig) before any filter
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public SecurityConfig(UserDetailsService userDetailsService,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationEntryPoint=jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter=jwtAuthenticationFilter;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf)->csrf.disable()).authorizeHttpRequests((authorize)->
		         //authorize.anyRequest().authenticated()
		         authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
		         .requestMatchers("/api/auth/**").permitAll()
		         .requestMatchers("/swagger-ui/**").permitAll()
		         .requestMatchers("/v3/api-docs/**").permitAll()
		         .anyRequest().authenticated()
				).httpBasic(Customizer.withDefaults()
				).exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						
				);
	
		////configure this filter in spring securities (SecurityConfig) before any filter
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 
		return http.build();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
		
	}
	
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
	}
	
	/* In Memory Authentication
	 * @Bean public UserDetailsService userDetailsService() { UserDetails
	 * sankh=User.builder().username("sankh")
	 * .password(passwordEncoder().encode("sankh")) .roles("USER") .build();
	 * 
	 * 
	 * UserDetails admin=User.builder() .username("admin")
	 * .password(passwordEncoder().encode("admin")) .roles("ADMIN") .build();
	 * 
	 * 
	 * return new InMemoryUserDetailsManager(sankh,admin);
	 * 
	 * }
	 */

}
