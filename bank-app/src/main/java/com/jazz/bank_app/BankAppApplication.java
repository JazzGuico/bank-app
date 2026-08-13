package com.jazz.bank_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner testRunner(SomeService someService) {
		return args -> {
			System.out.println("Spring gave us: " + someService);
		};
	}
}