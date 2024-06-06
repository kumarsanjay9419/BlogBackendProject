package com.sk.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
	
	private int likeId;
	private int counter;
	private UserDto user;
	private PostDto post;

}