package com.example.ebankinbackend;

import com.example.ebankinbackend.entities.*;
import com.example.ebankinbackend.enums.Operationtype;
import com.example.ebankinbackend.exeption.BalanceNotSuffisantException;
import com.example.ebankinbackend.exeption.BankAccountNotFoundException;
import com.example.ebankinbackend.exeption.CustomernotfoundExeption;
import com.example.ebankinbackend.mapers.CustomerDTO;
import com.example.ebankinbackend.repositories.BankAccountRepository;
import com.example.ebankinbackend.repositories.CustumorRepository;
import com.example.ebankinbackend.repositories.OperationRepository;
import com.example.ebankinbackend.service.BankAccountService;
import enums.AccountStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.imageio.ImageTranscoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankinBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankinBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
        Stream.of("hassan","mohammed","imane").forEach(nom->{
            CustomerDTO customer=new CustomerDTO();
            customer.setNom(nom);
            customer.setEmail(nom+"@gmail.com");
            bankAccountService.saveCustomer(customer);
        });
        bankAccountService.listcustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 9000, 9000, customer.getId(), customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 9000, 5.5, customer.getId(), customer.getId());
                    List<BankAccount> bankAccounts = bankAccountService.BankAccountlist();
                    for (BankAccount bankAccount : bankAccounts) {
                        for (int i = 0; i < 10; i++) {
                            bankAccountService.credit(bankAccount.getId(), 10000 + Math.random() * 120000, "Credit");
                     bankAccountService.debit(bankAccount.getId(),1000+Math.random()*9000,"Debit");
                        }
                    }
                } catch (BalanceNotSuffisantException e) {
                    throw new RuntimeException(e);
                } catch (BankAccountNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (CustomernotfoundExeption e) {
                    throw new RuntimeException(e);
                }
        });

        };
        }

        @Bean
        CommandLineRunner start(CustumorRepository custumorRepository, BankAccountRepository bankAccountRepository, OperationRepository OperationRepository) {
          return args -> {
              Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                  Customer customer=new Customer();
                  customer.setNom(name);
                  customer.setEmail(name+"@gmail.com");
                  custumorRepository.save(customer);
              });
              custumorRepository.findAll().forEach(cust-> {
                  CurrentAccount currentAccount=new CurrentAccount();
                  currentAccount.setId(UUID.randomUUID().toString());
                    currentAccount.setCreatedAt(new Date());
                    currentAccount.setSolde(Math.random()*900000);
                    currentAccount.setStatus(AccountStatus.CREATED);
                    currentAccount.setCustomer(cust);
                    currentAccount.setOverDraft(9000);
                  bankAccountRepository.save(currentAccount);

                      SavingAccount savingAccount=new SavingAccount();
                  currentAccount.setId(UUID.randomUUID().toString());
                  savingAccount.setCreatedAt(new Date());
                  savingAccount.setSolde(Math.random()*900000);
                  savingAccount.setStatus(AccountStatus.CREATED);
                  savingAccount.setCustomer(cust);
                  savingAccount.setInterestRate(5.5);
                  bankAccountRepository.save(savingAccount);

              });
              bankAccountRepository.findAll().forEach(acc->{
                  for (int i=0;i<10;i++)
                  {
                      Opperation operation =new Opperation();
                      operation.setDate(new Date());
                      operation.setAmount(Math.random()*900000);
                      operation.setType(Math.random()>0.5? Operationtype.DEBIT:Operationtype.CREDIT);
                      operation.setBankAccount(acc);
                     OperationRepository.save(operation);
                  }
              });
          };

        }
    }

