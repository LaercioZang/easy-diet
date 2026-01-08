package com.easydiet.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EasyDietBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyDietBackendApplication.class, args);
	}

}
