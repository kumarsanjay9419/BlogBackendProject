package com.sk.blog.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvalidUserDetailsException extends RuntimeException{
	
	private String resourceName;
	private String value;
	private Integer status;
	public InvalidUserDetailsException(String resourceName, String value, Integer status) {
		super(String.format("%s Invalid with passed value %s, status : %s", resourceName, value, status));
		this.resourceName = resourceName;
		this.value = value;
		this.status = status;
	}
	
	
}