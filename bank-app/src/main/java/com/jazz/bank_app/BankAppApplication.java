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
	public CommandLineRunner testRunner(AccountService accountService) {
		return args -> {
			BankAccount jazzAccount = accountService.openAccount("Jazz");
			jazzAccount.deposit(500);

			BankAccount retrieved = accountService.getAccount("Jazz");
			System.out.println(retrieved.generateStatement());
		};
	}
}
