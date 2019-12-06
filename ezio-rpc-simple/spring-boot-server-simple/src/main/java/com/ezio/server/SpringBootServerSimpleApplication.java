package com.ezio.server;

import annotation.EnableServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableServiceRegistry
public class SpringBootServerSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServerSimpleApplication.class, args);
	}

}
