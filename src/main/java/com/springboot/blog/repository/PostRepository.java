package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Posts;

public interface PostRepository extends JpaRepository<Posts, Long> {
 
		List<Posts> findByCategoryId(Long categoryId); 
}
