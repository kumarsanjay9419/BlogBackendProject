package com.sk.blog.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sk.blog.entities.Category;
import com.sk.blog.entities.Like;
import com.sk.blog.entities.Post;
import com.sk.blog.entities.User;
import com.sk.blog.exception.ResourceNotFoundException;
import com.sk.blog.payloads.PostDto;
import com.sk.blog.payloads.PostResponse;
import com.sk.blog.repository.CategoryRepo;
import com.sk.blog.repository.PostRepo;
import com.sk.blog.repository.UserRepo;
import com.sk.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
//	@Autowired
//	LikeServiceImpl likeServiceImpl;
	
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", " userId ", userId));
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", " categoryId ", categoryId));
	
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = this.postRepo.save(post);
		
		// create like obj
		Like like = new Like();
		like.setCounter(0);
		like.setPost(createdPost);
		like.setUser(user);
		
	//	this.likeServiceImpl.createLike(like);
		
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", " postId ", postId));
		
//		Category category = this.categoryRepo.findById(Integer.valueOf(postDto.getCategoryId()))
//				.orElseThrow(()->new ResourceNotFoundException("Category", " categoryId ", Integer.valueOf(postDto.getCategoryId())));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		//post.setCategory(category);
		//if(postDto.getImageName()!=null)
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	//delete post
	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", " postId ",postId));
		this.postRepo.delete(post);
	}

	
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", " postId ", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	
	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
		
//		Sort sort=null;
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}else {
//			sort=Sort.by(sortBy).descending();
//		}
		
		//we can write above condition as 
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
//		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postsDto = new ArrayList<>();
		for(int i=0;i<posts.size();i++) {
			postsDto.add(this.modelMapper.map(posts.get(i), PostDto.class));
		}
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postsDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	
	//get by User
	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", " userId ", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}

	//get by Category
		@Override
		public List<PostDto> getPostByCategory(Integer categoryId) {
			Category category = this.categoryRepo.findById(categoryId)
					.orElseThrow(()->new ResourceNotFoundException("category", " categoryId ", categoryId));
			List<Post> posts = this.postRepo.findByCategory(category);
			List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			return postsDto;
		}

		//for searching....
		@Override
		public List<PostDto> searchPosts(String keyword) {
			List<Post> posts = this.postRepo.findByTitleContaining(keyword);
			List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			return postDtos;
		}
}

