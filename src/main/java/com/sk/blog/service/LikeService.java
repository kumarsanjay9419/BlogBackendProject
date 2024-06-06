package com.sk.blog.service;

import com.sk.blog.entities.Like;

public interface LikeService {

	void createLike(Like like);
	Integer updateLike(int likeCount, Integer likeId);
	void deleteLike(Integer likeId);
	
}
