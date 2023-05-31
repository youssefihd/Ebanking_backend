package com.example.ebankinbackend.service;

import com.example.ebankinbackend.entities.BankAccount;
import com.example.ebankinbackend.entities.CurrentAccount;
import com.example.ebankinbackend.entities.Customer;
import com.example.ebankinbackend.entities.SavingAccount;
import com.example.ebankinbackend.exeption.BalanceNotSuffisantException;
import com.example.ebankinbackend.exeption.BankAccountNotFoundException;
import com.example.ebankinbackend.exeption.CustomernotfoundExeption;
import com.example.ebankinbackend.mapers.CustomerDTO;

import java.util.List;

public interface BankAccountService {
    public CustomerDTO saveCustomer(CustomerDTO customer);
        CurrentAccount saveCurrentBankAccount (double initialBalance, double overdraft , Long id, Long customerId) throws CustomernotfoundExeption;


    SavingAccount saveSavingBankAccount (double initialBalance, double interestrate , Long id, Long customerId) throws CustomernotfoundExeption;

    List<CustomerDTO> listcustomers();
    BankAccount getbankAccount(String acccountid) throws BankAccountNotFoundException;
    void debit(String accountid,double amount ,String description) throws BalanceNotSuffisantException, BankAccountNotFoundException;
    void credit(String accountid,double amount ,String description) throws BankAccountNotFoundException ;
    void transfert(String accountid,String accountdestination , double amount) throws BankAccountNotFoundException, BalanceNotSuffisantException;


    List<BankAccount> BankAccountlist();

    CustomerDTO getCustomer(Long Customerid) throws CustomernotfoundExeption;
    public void delete(Long id);
}
