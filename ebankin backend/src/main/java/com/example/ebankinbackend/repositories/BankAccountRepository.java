package com.example.ebankinbackend.repositories;

import com.example.ebankinbackend.entities.BankAccount;
import com.example.ebankinbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
