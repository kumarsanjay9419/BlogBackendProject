package com.sk.blog.service;

import com.sk.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	void deleteComment(Integer commentId);
	
}