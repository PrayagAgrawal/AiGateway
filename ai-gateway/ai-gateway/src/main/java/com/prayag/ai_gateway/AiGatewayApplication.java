package com.prayag.ai_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.prayag.ai_gateway.repository")
@EntityScan(basePackages = "com.prayag.ai_gateway.entity")
public class AiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiGatewayApplication.class, args);
	}

}
