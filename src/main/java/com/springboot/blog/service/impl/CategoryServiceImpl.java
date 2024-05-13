package com.springboot.blog.service.impl;



import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtopaylod.CategoryDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;

import jakarta.persistence.NoResultException;

@Service
public class CategoryServiceImpl implements CategoryService{
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository,ModelMapper modelMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category=modelMapper.map(categoryDto,Category.class);
		   Category savedCategory=  categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	} 

	@Override
	public CategoryDto getCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category= categoryRepository.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getALlCategories() {
		// TODO Auto-generated method stub
		List<Category> categories= categoryRepository.findAll();
		
	return categories.stream().map((category)->modelMapper.map(category, CategoryDto.class))
			.collect(Collectors.toList()); 
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		// TODO Auto-generated method stub
	Category category=	categoryRepository.findById(categoryId)
		.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
	
	
	category.setName(categoryDto.getName());
	category.setDescription(categoryDto.getDescription());
	category.setId(categoryId);
	Category updatedCategory =categoryRepository.save(category);
	
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category=categoryRepository.findById(categoryId)
		.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId)); 
		categoryRepository.delete(category);
	}

}
