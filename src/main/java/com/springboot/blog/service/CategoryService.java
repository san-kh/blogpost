package com.springboot.blog.service;



import java.util.List;

import com.springboot.blog.dtopaylod.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto); 
	CategoryDto getCategory(Long categoryId);
	List<CategoryDto> getALlCategories();
	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	void deleteCategory(Long categoryId);
	

}
