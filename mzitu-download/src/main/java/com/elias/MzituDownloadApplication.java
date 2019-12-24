package com.elias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MzituDownloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MzituDownloadApplication.class, args);
	}

}
