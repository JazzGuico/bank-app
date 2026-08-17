package com.jazz.bank_app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    boolean existsByAccountHolder(String accountHolder);
}
