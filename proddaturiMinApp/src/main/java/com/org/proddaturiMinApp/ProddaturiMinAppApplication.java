
package com.org.proddaturiMinApp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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


	@Bean
	public OpenAPI createBeanForOpenAPI(){
		return new OpenAPI().info(new Info().description("Get everything in 20 minutes").title("ProddaturiMinAppApplication").version("1.0.0"));
	}
}

