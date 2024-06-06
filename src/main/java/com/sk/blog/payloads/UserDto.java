package com.sk.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private int id;
	@NotEmpty
	@Size(min = 4, message = "Username must be greater than 4 characters !!")
	private String name;
	@NotEmpty(message = "Email is required !!")
	@Email(message = "Email address is not valid !!")
	private String email;
	@NotEmpty(message = "Password cannot be empty !!")
	@Size(min = 3, max = 10, message = "Password must be in between 3 and 10 characters !!")
//	@Pattern(regexp = )
	private String password;
	@NotEmpty
	private String about;

}
