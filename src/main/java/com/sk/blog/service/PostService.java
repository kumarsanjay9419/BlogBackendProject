package com.sk.blog.service;

import java.util.List;

import com.sk.blog.payloads.PostDto;
import com.sk.blog.payloads.PostResponse;

public interface PostService {
	
	    //create
		PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
		//update
		PostDto updatePost(PostDto postDto, Integer postId);
		//delete
		void deletePost(Integer postId);
	
	// get Single Post
	PostDto getPostById(Integer postId);
	//get All posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	//get all post by User
	List<PostDto> getPostByUser(Integer userId);
	//get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	//search post
	List<PostDto> searchPosts(String keyword);
	
	
}

