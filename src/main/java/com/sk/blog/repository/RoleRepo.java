package com.sk.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.blog.entities.Role;


public interface RoleRepo extends JpaRepository<Role, Integer> {

}
