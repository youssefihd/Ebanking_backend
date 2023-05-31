package com.example.ebankinbackend.entities;

import enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.Length;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.util.Lazy;

import java.util.Date;
import java.util.List;
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)
public class BankAccount {
    @Id
    private String id;
    private double solde;
    private Date createdAt;
    private AccountStatus status;
@ManyToOne
    private Customer customer;
@OneToMany(mappedBy = "bankAccount",fetch =FetchType.LAZY)
private List<Opperation> opperations;
}
