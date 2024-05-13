package com.springboot.blog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST API's",
				description = "Blog App REST API's Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Sandip Vilas Khairmode",
						email = "sankhjavahub@gmail.com",
						url = "http://localhost:8080/api/auth/login"
						),
				license = @License(
						name = "Apache 2.0",
						url = "http://localhost:8080/api/auth/login"
						
						)
				
				),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot blog app documentation",
				url = "http://localhost:8080/api/auth/login"
				)
		)
public class SpringbootBlogRestApi2024Application  implements CommandLineRunner{
	
		public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApi2024Application.class, args);
		System.out.println("============Springboot Blog2024 Application====================");
	}

		@Autowired
		private RoleRepository roleRepository;
		
		@Override
		public void run(String... args) throws Exception {
			// TODO Auto-generated method stub
			  Role adminRole=new Role();
			  adminRole.setName("ROLE_ADMIN");
			  
			  roleRepository.save(adminRole);
			  
			  Role userRole=new Role(); 
			  userRole.setName("ROLE_USER");
			  
			  roleRepository.save(userRole);
		}
}