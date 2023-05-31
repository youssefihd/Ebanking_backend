package com.example.ebankinbackend.entities;

import com.example.ebankinbackend.enums.Operationtype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Opperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private Operationtype type;
    @ManyToOne
    private BankAccount bankAccount;
    private String description;

}
