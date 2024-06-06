package com.sk.blog.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.blog.entities.Comment;
import com.sk.blog.entities.Post;
import com.sk.blog.entities.User;
import com.sk.blog.exception.ResourceNotFoundException;
import com.sk.blog.payloads.CommentDto;
import com.sk.blog.repository.CommentRepo;
import com.sk.blog.repository.PostRepo;
import com.sk.blog.repository.UserRepo;
import com.sk.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired 
	CommentRepo commentRepo;
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", " userId ", userId));
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", " postId ", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", " commentId ", commentId));
		
		this.commentRepo.delete(comment);

	}

}

