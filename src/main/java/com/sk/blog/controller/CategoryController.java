package com.sk.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.blog.constants.UserConstant;
import com.sk.blog.payloads.ApiResponse;
import com.sk.blog.payloads.CategoryDto;
import com.sk.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	
	//create 
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCat = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCat,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id){
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	//fetch by Id
		@GetMapping("/{id}")
		public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id){
			CategoryDto searchCategory = this.categoryService.getCategory(id);
			return new ResponseEntity<CategoryDto>(searchCategory,HttpStatus.FOUND);
		}
		
		//fetch all
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getAllCategories(){
			return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategory(), HttpStatus.OK);
		}
		
	//delete by Id
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse(UserConstant.CATEGORY_DELETE, true), HttpStatus.OK);
	}
	
}
