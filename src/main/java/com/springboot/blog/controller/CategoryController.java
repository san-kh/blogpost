package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtopaylod.CategoryDto;
import com.springboot.blog.service.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
		
	}
	//add category REST API
	@SecurityRequirement(name ="Bearer Authentication" )
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto CategoryDto) {
		CategoryDto  savedCategoryDto= categoryService.addCategory(CategoryDto);
		return new ResponseEntity<CategoryDto>(savedCategoryDto, HttpStatus.CREATED); 
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable(value = "id") long id) {
		CategoryDto categoryDto= categoryService.getCategory(id);
		return ResponseEntity.ok(categoryDto);
		
	}
	
	@GetMapping
	 public ResponseEntity<List<CategoryDto>> getCategories() {
		       
		return ResponseEntity.ok(categoryService.getALlCategories());
	}
	
	@SecurityRequirement(name ="Bearer Authentication" )
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") long categoryId) {
		
		CategoryDto updatedCategory= categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK); 
		
	}
	
	@SecurityRequirement(name ="Bearer Authentication" )
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.OK);
		
	}

}
