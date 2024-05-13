package com.springboot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtopaylod.PostDto;
import com.springboot.blog.dtopaylod.PostDtoV2;
import com.springboot.blog.dtopaylod.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping()//changed for versoning reason 11-march-24
@Tag(
		name = "CRUD API's for Posts Resource"
		)
public class PostController {

	//if we use inteface the we make loose coupling
    private PostService postService;
    //*****constructor based dependency injection
    public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

 
    @Operation(
    		summary = "Create POST REST API",
    		description = "Used to save Post into Database "
    		)
    @ApiResponse(	
    		responseCode = "201",
    		description = "Http Status 201 created"
    		)
    
    @SecurityRequirement(name ="Bearer Authentication" )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPosts(@Valid @RequestBody PostDto postDto) {
		
    	return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED); 
		
	}
    
    @Operation(
    		summary = "Used to get All posts",
    		description = "Used to get All posts"
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    @GetMapping("/api/v1/posts")
    public PostResponse  getAllPosts(@RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false) int pageNo,
    		@RequestParam(value = "pageSize",required = false,defaultValue=AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
    		@RequestParam(value = "sortBy",required = false,defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
    		@RequestParam(value = "sortOrder",required = false,defaultValue = AppConstants.DEFAULT_SORT_ORDER) String sortOrder) {
		return postService.getAllPosts(pageNo,pageSize,sortBy,sortOrder);    
		
	}
    
  //versioning with URI path variable v1
    
    @Operation(
    		summary = "GET POST BY ID REST API",
    		description = "Used to get Post from Databse on provided PostId "
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") long id) { 
    	
		return ResponseEntity.ok(postService.getPostById(id));
		
	}
    
    //v2
    @GetMapping("/api/v2/posts/{id}")
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") long id) { 
    	
    	PostDto postDto=postService.getPostById(id);
    	
    	PostDtoV2 postDtoV2=new PostDtoV2();
    	
    	postDtoV2.setId(postDto.getId());
    	postDtoV2.setTitle(postDto.getTitle());
    	postDtoV2.setContent(postDto.getContent());
    	postDtoV2.setDescription(postDto.getDescription());
    	List<String> tags=new ArrayList<String>();
    	tags.add("java");
    	tags.add("SpringBoot");
    	tags.add("AWS"); 
    	postDtoV2.setTags(tags);
    	
		return ResponseEntity.ok(postDtoV2);
		
	}
    
    
  //versioning with Query param variable v1
    
    @Operation(
    		summary = "GET POST BY ID REST API",
    		description = "Used to get Post from Databse on provided PostId "
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    @GetMapping(value = "/api/posts/{id}",params = "version=1")
    public ResponseEntity<PostDto> getPostById_QueryParamV1(@PathVariable(name = "id") long id) { 
    	
		return ResponseEntity.ok(postService.getPostById(id));
		
	}
    
    //v2
    @GetMapping(value =  "/api/posts/{id}",params = "version=2")
    public ResponseEntity<PostDtoV2> getPostById_QueryParamV2(@PathVariable(name = "id") long id) { 
    	
    	PostDto postDto=postService.getPostById(id);
    	
    	PostDtoV2 postDtoV2=new PostDtoV2();
    	
    	postDtoV2.setId(postDto.getId());
    	postDtoV2.setTitle(postDto.getTitle());
    	postDtoV2.setContent(postDto.getContent());
    	postDtoV2.setDescription(postDto.getDescription());
    	List<String> tags=new ArrayList<String>();
    	tags.add("java");
    	tags.add("SpringBoot");
    	tags.add("AWS"); 
    	postDtoV2.setTags(tags);
    	
		return ResponseEntity.ok(postDtoV2);
		
	}
    
    
 //versioning with header param  v1
    
    @Operation(
    		summary = "GET POST BY ID REST API",
    		description = "Used to get Post from Databse on provided PostId "
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    @GetMapping(value = "/api/posts/{id}",headers = "X-API-VERSION=1")
    public ResponseEntity<PostDto> getPostById_HeaderParamV1(@PathVariable(name = "id") long id) { 
    	
		return ResponseEntity.ok(postService.getPostById(id));
		
	}
    
    //versioning with header param  v2
    @GetMapping(value =  "/api/posts/{id}",headers = "X-API-VERSION=2")
    public ResponseEntity<PostDtoV2> getPostById_HeaderParamV2(@PathVariable(name = "id") long id) { 
    	
    	PostDto postDto=postService.getPostById(id);
    	
    	PostDtoV2 postDtoV2=new PostDtoV2();
    	
    	postDtoV2.setId(postDto.getId());
    	postDtoV2.setTitle(postDto.getTitle());
    	postDtoV2.setContent(postDto.getContent());
    	postDtoV2.setDescription(postDto.getDescription());
    	List<String> tags=new ArrayList<String>();
    	tags.add("java");
    	tags.add("SpringBoot");
    	tags.add("AWS"); 
    	postDtoV2.setTags(tags);
    	
		return ResponseEntity.ok(postDtoV2);
		
	}
    
    
    
 //versioning with Content Negotiations  v1
    
    @Operation(
    		summary = "GET POST BY ID REST API",
    		description = "Used to get Post from Databse on provided PostId "
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    @GetMapping(value = "/api/posts/{id}",produces = "application/v1+json")
    public ResponseEntity<PostDto> getPostById_ContentNegotiationV1(@PathVariable(name = "id") long id) { 
    	
		return ResponseEntity.ok(postService.getPostById(id)); 
		
	}
    
    
 //versioning with Content Negotiations  v2
    @GetMapping(value =  "/api/posts/{id}",produces = "application/v2+json") 
    public ResponseEntity<PostDtoV2> getPostById_ContentNegotiationV2(@PathVariable(name = "id") long id) { 
    	
    	PostDto postDto=postService.getPostById(id);
    	
    	PostDtoV2 postDtoV2=new PostDtoV2();
    	
    	postDtoV2.setId(postDto.getId());
    	postDtoV2.setTitle(postDto.getTitle());
    	postDtoV2.setContent(postDto.getContent());
    	postDtoV2.setDescription(postDto.getDescription());
    	List<String> tags=new ArrayList<String>();
    	tags.add("java");
    	tags.add("SpringBoot");
    	tags.add("AWS"); 
    	postDtoV2.setTags(tags);
    	
		return ResponseEntity.ok(postDtoV2);
		
	}
    
    
    
    
    
    
    
    
    @Operation(
    		summary = "Update POST By ID REST API",
    		description = "Used to get update post into Databse on provided PostId and post data in request body "
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    @SecurityRequirement(name ="Bearer Authentication" )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") long id) {
    	PostDto postResponse=postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
		
	}
    
    @Operation(
    		summary = "Delete POST By ID REST API",
    		description = "Used to delete post from Databse on provided PostId"
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    
    @SecurityRequirement(name ="Bearer Authentication" )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
    	postService.deletePostbyID(id);
		return new ResponseEntity<String>("Post Entity Deleted Sucessfully", HttpStatus.OK);
    	
    } 
    
    @Operation(
    		summary = "Used to get All posts belongs to provided CategoryId",
    		description = "Used to get All posts belongs to provided CategoryId"
    		)
    @ApiResponse(	
    		responseCode = "200",
    		description = "Http Status 200 SUCCESS"
    		)
    //Build get Post by category REST API
    //http://localhost:8080/api/posts/category/3
    @GetMapping("/api/v1/posts/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
    	List<PostDto> postDtos= postService.getPostByCategory(categoryId);
		return ResponseEntity.ok(postDtos);
   	
	}
    

}
