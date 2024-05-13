package com.springboot.blog.dtopaylod;


import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
		description = "PostDto model information"
		)
public class PostDto {

	private long id;
	
	@Schema(
			description = "Blog Post Title"
			)
	@NotEmpty
	@Size(min = 2,message = "Post tilte Should have atleast 2 characters")
	private String title;
	
	@Schema(
			description = "Blog Post Description"
			)
	@NotEmpty
	@Size(min = 10,message = "Post description Should have atleast 10 characters")
	private String description;
	
	@Schema(
			description = "Blog Post Content"
			)
	@NotEmpty
	private String content;
	
	@Schema(
			description = "Blog Post comments"
			)
	private Set<CommentDto> comments; 
	
	@Schema(
			description = "Blog Post categoryId"
			)
	//add categories to the Posts
	private Long categoryId; 
	
}
