package com.springboot.blog.dtopaylod;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	
	private long id;
	
	@NotEmpty(message = "Name should not be empty or null")
	private String name;
	
	@NotEmpty(message = "Email should not be empty or null")
	@Email
	private String email;
	
	@NotEmpty(message = "Body should not be empty or null")
	@Size(min = 10,message = "Comment should atleast 10 charecters") 
	private String body;

}
