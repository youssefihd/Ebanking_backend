package com.example.ebankinbackend.repositories;

import com.example.ebankinbackend.entities.Customer;
import com.example.ebankinbackend.entities.Opperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Opperation,Long> {
}
