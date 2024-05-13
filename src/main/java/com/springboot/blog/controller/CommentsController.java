package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtopaylod.CommentDto;
import com.springboot.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentsController {
	
	
	private CommentService commentService;

	public CommentsController(CommentService commentService) {
		super();
		this.commentService = commentService; 
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
			@Valid @RequestBody CommentDto commentDto) { 
      //System.out.println("********************"+postId); 
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
		
	}
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getAllCommentsByPostID(@PathVariable(value = "postId") long postId){
		
		return commentService.getAllCommentsByPostID(postId);
		
	}
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value ="postId" ) long postId ,@PathVariable(value = "commentId") long commentId) {
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		
		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
		
		
	}
	@PutMapping("/posts/{postId}/comments/{commentId}") 
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value ="postId" ) long postId ,@PathVariable(value = "commentId") long commentId,
			@Valid @RequestBody CommentDto commentDto) {
		CommentDto updatedcommentDto = commentService.updateComment(postId, commentId, commentDto);  
		
		return new ResponseEntity<CommentDto>(updatedcommentDto, HttpStatus.OK); 
		
	}
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(value ="postId" ) long postId ,@PathVariable(value = "commentId") long commentId) {
	          commentService.deleteComment(postId, commentId);  
	          
		return new ResponseEntity<>("Comment Deleted Sucessfully",HttpStatus.OK);  
		
	}

}
