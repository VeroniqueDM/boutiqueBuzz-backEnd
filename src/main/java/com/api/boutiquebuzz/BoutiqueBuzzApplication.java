package com.api.boutiquebuzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@ComponentScan(basePackages = {"com.api.boutiquebuzz", "com.api.boutiquebuzz.config"})
public class BoutiqueBuzzApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoutiqueBuzzApplication.class, args);
	}

}
