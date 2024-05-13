package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtopaylod.PostDto;
import com.springboot.blog.dtopaylod.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Posts;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CategoryService;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	private PostRepository postRepository; 
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;
	
	//****** Constructor based dependency injection 
	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,
			CategoryRepository categoryRepository) { 
		super();
		this.postRepository = postRepository; 
		this.mapper=mapper; 
		this.categoryRepository=categoryRepository;
	}
	
	private PostDto maptoDto(Posts posts) {
		PostDto postDto=mapper.map(posts, PostDto.class);
		/*
		 * PostDto postDto=new PostDto(); postDto.setId(posts.getId());  
		 * postDto.setTitle(posts.getTitle());
		 * postDto.setDescription(posts.getDescription());
		 * postDto.setContent(posts.getContent());
		 */
		return postDto; 
		
	}
	private Posts mapToEntity(PostDto postDto) {
		Posts posts=mapper.map(postDto, Posts.class);
		/*
		 * Posts posts=new Posts(); posts.setTitle(postDto.getTitle());
		 * posts.setDescription(postDto.getDescription());
		 * posts.setContent(postDto.getContent());
		 */
		return posts; 
		
	}
	

    @Override
	public PostDto createPost(PostDto postDto) {
		// TODO Auto-generated method stub
    	
    	Category category=categoryRepository.findById(postDto.getCategoryId())
    			.orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
    	
    	//*** convert Dto to entity
    	Posts posts=mapToEntity(postDto);
    	posts.setCategory(category);
		
		Posts newPost= postRepository.save(posts);
		
		//*** convert entity to Dto
		PostDto postResponce=maptoDto(newPost);
		return postResponce;
	}

	@Override
	public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortOrder) {
		// TODO Auto-generated method stub
		//creating Sort object to Sort by order
		Sort sort=sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(Direction.ASC, sortBy):
			Sort.by(Direction.DESC,sortBy);
		
		//create pageable instance
		//Pageable pageable=PageRequest.of(pageNo, pageSize);
		
		//pagination+sorting 
		//Pageable pageable=PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		//pagination+sorting by order 
		Pageable pageable=PageRequest.of(pageNo, pageSize,sort);  
		
		//pass this 'pageable' instance to 'findAll(Pagaeable instance)'method of repository
		Page<Posts> pagePosts= postRepository.findAll(pageable);
		     //get content from page object
		      List<Posts> posts=pagePosts.getContent();
		   
		      
		      //convert List<Posts> into List<PostDto>
		      //With using stream we get all posts
		      List<PostDto> getAllPosts= posts.stream().map(i->maptoDto(i)).collect(Collectors.toList());
		      PostResponse postResponse=new PostResponse();
		      postResponse.setContent(getAllPosts);
		      postResponse.setPageNo(pagePosts.getNumber());
		      postResponse.setPageSize(pagePosts.getSize());
		      postResponse.setTotalElements(pagePosts.getTotalElements());
		      postResponse.setTotalPages(pagePosts.getTotalPages());
		      postResponse.setLast(pagePosts.isLast());
		    return postResponse;
				
	}
	
	@Override
	public PostDto getPostById(long id) {
		// TODO Auto-generated method stub
		Posts post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		return maptoDto(post);
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// TODO Auto-generated method stub
		Posts post=postRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		
		Category category=categoryRepository.findById(postDto.getCategoryId())
    			.orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent()); 
		post.setCategory(category);
		Posts updatedPost=postRepository.save(post);
		return maptoDto(updatedPost); 
	}

	@Override
	public void deletePostbyID(long id) {
		// TODO Auto-generated method stub
		Posts post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Posts", "id", id));
		//postRepository.deleteById(id);
		postRepository.delete(post);
		
	}

	@Override
	public List<PostDto> getPostByCategory(Long categoryId) {
		Category category=categoryRepository.findById(categoryId)
    			.orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		List<Posts> posts=postRepository.findByCategoryId(categoryId);
		
		return posts.stream()
				.map((post)->maptoDto(post))
				.collect(Collectors.toList());
	}

	
	
	

	
	
	

}
