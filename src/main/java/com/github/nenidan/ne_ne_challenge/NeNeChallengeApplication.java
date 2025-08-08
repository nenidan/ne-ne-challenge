package com.github.nenidan.ne_ne_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class NeNeChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeNeChallengeApplication.class, args);
	}

}
