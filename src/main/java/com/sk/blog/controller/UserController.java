package com.sk.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.blog.constants.UserConstant;
import com.sk.blog.payloads.ApiResponse;
import com.sk.blog.payloads.UserDto;
import com.sk.blog.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
    //For Create
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUser = this.userService.creteUser(userDto);
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
	}
	//for update
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable(name = "id") int id){
		UserDto updatedUser = this.userService.updateUser(userDto, id);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
	}
	
	//for fetch all data
		@GetMapping("/")
		public ResponseEntity<List<UserDto>> getAllUser() {
			return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(), HttpStatus.FOUND);
		}
	//fetch the data by Id
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") int id) {
		UserDto userDto = this.userService.getUserById(id);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.FOUND);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable(name = "id") int id) {
		this.userService.deleteUser(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse(UserConstant.USER_DELETE, true), HttpStatus.OK);
	}
	

}
