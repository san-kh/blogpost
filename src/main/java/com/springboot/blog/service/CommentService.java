package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dtopaylod.CommentDto;

public interface CommentService {
	CommentDto createComment(long postId,CommentDto commentDto);
	List<CommentDto> getAllCommentsByPostID(long postId);
	CommentDto getCommentById(long postId,long commentId);
	CommentDto updateComment(long postId,long commentId,CommentDto commentDto); 
	void deleteComment(long postId,long commentId);

}
