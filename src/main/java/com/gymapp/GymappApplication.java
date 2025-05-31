package com.gymapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class GymappApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymappApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

}
