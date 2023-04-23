package com.markskroba.devconnectorspringboot;

import com.markskroba.devconnectorspringboot.posts.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DevconnectorSpringBootApplication {

	@Autowired
	PostRepository postRepository;
	public static void main(String[] args) {
		SpringApplication.run(DevconnectorSpringBootApplication.class, args);
	}

}
