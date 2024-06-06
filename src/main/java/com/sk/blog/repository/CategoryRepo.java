package com.sk.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
