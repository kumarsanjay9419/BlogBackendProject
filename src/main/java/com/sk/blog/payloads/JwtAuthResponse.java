package com.sk.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
	
	private String token;
	private UserDto user;

}