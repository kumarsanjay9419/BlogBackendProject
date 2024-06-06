package com.sk.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sk.blog.config.AppConstants;
import com.sk.blog.entities.Role;
import com.sk.blog.repository.RoleRepo;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BlogAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Override
	public void run(String... args) throws Exception {
		//System.out.println(this.passwordEncoder.encode("abc"));	
		
		try {
			Role role = new Role(AppConstants.NORMAL_USER, "NORMAL_USER");
			Role role1 = new Role(AppConstants.ADMIN_USER, "ADMIN_USER");
			
			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r ->{
				System.out.println(r.getName());
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
