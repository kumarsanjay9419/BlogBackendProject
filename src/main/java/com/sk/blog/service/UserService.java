package com.sk.blog.service;

import java.util.List;

import com.sk.blog.payloads.UserDto;
public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);
	UserDto creteUser(UserDto userDto);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
