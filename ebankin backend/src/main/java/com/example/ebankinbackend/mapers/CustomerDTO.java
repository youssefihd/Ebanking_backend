package com.example.ebankinbackend.mapers;

import com.example.ebankinbackend.entities.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class CustomerDTO {
    private Long id;
    private String nom;
    private String email;

}
