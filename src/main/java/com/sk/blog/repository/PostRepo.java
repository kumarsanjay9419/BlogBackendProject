package com.sk.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.blog.entities.Category;
import com.sk.blog.entities.Post;
import com.sk.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	//custom methods 
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);

}