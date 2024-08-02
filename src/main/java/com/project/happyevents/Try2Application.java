package com.project.happyevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.happyevents"})
public class Try2Application {

	public static void main(String[] args) {
		SpringApplication.run(Try2Application.class, args);
	}

}
