package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dtopaylod.PostDto;
import com.springboot.blog.dtopaylod.PostResponse;
import com.springboot.blog.entity.Posts;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortOrder); 
	PostDto getPostById(long id); 
	PostDto updatePost(PostDto postDto,long id); 
	void deletePostbyID(long id); 
	List<PostDto> getPostByCategory(Long categoryId);

}
