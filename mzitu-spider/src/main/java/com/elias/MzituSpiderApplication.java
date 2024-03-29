package com.elias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MzituSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MzituSpiderApplication.class, args);
	}

}
