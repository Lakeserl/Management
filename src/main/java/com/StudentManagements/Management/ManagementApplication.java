package com.StudentManagements.Management;

import	com.StudentManagements.config.CurrentUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.StudentManagements.repository")
public class ManagementApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}
	
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver()); 
    }
}
