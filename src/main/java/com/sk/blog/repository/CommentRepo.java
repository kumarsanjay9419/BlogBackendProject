package com.sk.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
