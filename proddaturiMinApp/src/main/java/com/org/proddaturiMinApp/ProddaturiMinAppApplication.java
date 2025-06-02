package com.org.proddaturiMinApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProddaturiMinAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProddaturiMinAppApplication.class, args);
	}


}
