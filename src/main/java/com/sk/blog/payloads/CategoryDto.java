package com.sk.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4, message = "Category must be greater than 4 characters")
	private String categoryTitle;
	@NotEmpty
	@Size(min = 10, message = "Category size  must be between 0 and 10 characters")
	private String categoryDescription;


}
