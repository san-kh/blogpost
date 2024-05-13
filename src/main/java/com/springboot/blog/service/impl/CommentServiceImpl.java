package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtopaylod.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Posts;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	 private CommentRepository commentRepository;
	 private PostRepository postRepository;
	 private ModelMapper mapper;
	 
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper=mapper;
		
	}
	
	
	//write DTO methods
	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto=mapper.map(comment, CommentDto.class); 
		
		/*
		 * CommentDto commentDto=new CommentDto(); commentDto.setId(comment.getId());
		 * commentDto.setName(comment.getName());
		 * commentDto.setEmail(comment.getEmail());
		 * commentDto.setBody(comment.getBody());
		 */
		return commentDto;
	}
	
	private Comment mapToEntity(CommentDto commentDto) { 
		Comment comment=mapper.map(commentDto,Comment.class); 
		
		/*
		 * Comment comment=new Comment(); comment.setId(commentDto.getId());
		 * comment.setName(commentDto.getName());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setBody(commentDto.getBody());
		 */
		
		return comment; 
		
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		//map commentDto to entity
		Comment comment=mapToEntity(commentDto);
		//Retrieve post entity by id
		
		Posts post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		//set post to comment entity
		comment.setPost(post);
		//save the comment entity
		Comment newComment=commentRepository.save(comment); 
	
		return mapToDto(newComment); 
	}
	
	@Override
	public List<CommentDto> getAllCommentsByPostID(long postId) {
		// TODO Auto-generated method stub
		List<Comment> comments=commentRepository.findByPostId(postId);
		return comments.stream().map(i->mapToDto(i)).collect(Collectors.toList());
		
	}
	 
	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		// TODO Auto-generated method stub
		//check postId Exists & retrieve Post entity by id
		Posts post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		//check CommnetId exists & retrieve comment entity by id
		Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new  BlogAPIException(HttpStatus.BAD_REQUEST, "Commnet does not belongs to post");
		}
		
		return mapToDto(comment);
	}


	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		// TODO Auto-generated method stub
				//check postId Exists & retrieve Post entity by id
				Posts post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
				//check CommnetId exists & retrieve comment entity by id
				Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
				
				
				if(!comment.getPost().getId().equals(post.getId())) {
					throw new  BlogAPIException(HttpStatus.BAD_REQUEST, "Commnet does not belongs to post");
				}
				comment.setName(commentDto.getName());
				comment.setEmail(commentDto.getEmail());
				comment.setBody(commentDto.getBody());
				
				Comment updatedComment=commentRepository.save(comment);
				
		
		return mapToDto(updatedComment);
	}


	@Override
	public void deleteComment(long postId, long commentId) {
		// TODO Auto-generated method stub
		Posts post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		//check CommnetId exists & retrieve comment entity by id
		Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new  BlogAPIException(HttpStatus.BAD_REQUEST, "Commnet does not belongs to post");
		}
		commentRepository.delete(comment); 
		
	}



	

	
	 

}
