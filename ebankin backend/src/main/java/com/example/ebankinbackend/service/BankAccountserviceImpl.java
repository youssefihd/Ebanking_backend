package com.example.ebankinbackend.service;

import com.example.ebankinbackend.entities.*;
import com.example.ebankinbackend.enums.Operationtype;
import com.example.ebankinbackend.exeption.BalanceNotSuffisantException;
import com.example.ebankinbackend.exeption.BankAccountNotFoundException;
import com.example.ebankinbackend.exeption.CustomernotfoundExeption;
import com.example.ebankinbackend.mapers.CustomerDTO;
import com.example.ebankinbackend.mapers.Mappers;
import com.example.ebankinbackend.repositories.BankAccountRepository;
import com.example.ebankinbackend.repositories.CustumorRepository;
import com.example.ebankinbackend.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
@Data
public class BankAccountserviceImpl implements BankAccountService {
    @Autowired
    private CustumorRepository custumorRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private OperationRepository operationRepository;
    private Mappers mapperDTO;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        logger.info("Saving new customer");
        Customer customer=mapperDTO.fromcustomerDTO(customerDTO)
        Customer savedcust = custumorRepository.save(customer);
        return mapperDTO.fromCustomer(savedcust);
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestrate, Long id, Long customerId) throws CustomernotfoundExeption {
        SavingAccount savingAccount = new SavingAccount();
        Customer customer = custumorRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomernotfoundExeption("Customer not found");
        }

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setSolde(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestrate);
        SavingAccount savedbankaccount=bankAccountRepository.save(savingAccount);
        return savedbankaccount;

    }


    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overdraft, Long id, Long customerId) throws CustomernotfoundExeption {
        BankAccount bankAccount;
        Customer customer=custumorRepository.findById(customerId).orElse(null);
        if(customer==null) {
            throw new CustomernotfoundExeption("Customer not found");
        }
        CurrentAccount currentAccount=new CurrentAccount();


        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setSolde(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overdraft);
        CurrentAccount savedba =bankAccountRepository.save(currentAccount);
        return savedba;
    }



    @Override
    public List<CustomerDTO> listcustomers() {
        List<Customer> customers = custumorRepository.findAll();
        List<CustomerDTO> customerDTOS=customers.stream().map(customer -> mapperDTO.fromCustomer(customer)).collect(Collectors.toList());
        for (Customer customer : customers){
        customers.stream().map(cust -> mapperDTO.fromCustomer(cust));


    }
        return customerDTOS;
    }

    @Override
    public BankAccount getbankAccount(String acccountid) throws BankAccountNotFoundException {
        BankAccount bankAccount;
        bankAccount = bankAccountRepository.findById(acccountid).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountid, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getbankAccount(accountid);


            Opperation opperation =new Opperation();
            opperation.setType(Operationtype.DEBIT);
            opperation.setAmount(amount);
            opperation.setDate(new Date());
            opperation.setDescription(description);
            operationRepository.save(opperation);
            opperation.setBankAccount(bankAccount);

            bankAccount.setSolde(bankAccount.getSolde()+amount);


        }
        @Override
        public void credit(String accountid,double amount ,String description) throws BankAccountNotFoundException {
            BankAccount bankAccount=getbankAccount(accountid);

                Opperation opperation =new Opperation();
                opperation.setType(Operationtype.CREDIT);
                opperation.setAmount(amount);
                opperation.setDate(new Date());
                opperation.setDescription(description);
                operationRepository.save(opperation);
                opperation.setBankAccount(bankAccount);

                bankAccount.setSolde(bankAccount.getSolde()-amount);
                bankAccountRepository.save(bankAccount);


            }

    @Override
    public void transfert(String accountidsource, String accountdestination, double amount) throws BankAccountNotFoundException,BalanceNotSuffisantException {
     debit(accountidsource,amount,"transfert to "+accountdestination);
credit(accountdestination,amount,"transfert from "+accountidsource);
    }
    @Override
    public List<BankAccount> BankAccountlist(){
        return bankAccountRepository.findAll();
    }
    @Override
    public CustomerDTO getCustomer(Long Customerid) throws CustomernotfoundExeption {
        Customer customer = custumorRepository.findById(Customerid).orElseThrow(() -> new CustomernotfoundExeption("Customer not founnd"));
        return mapperDTO.fromCustomer(customer);
    }
    public void delete(Long id){
        custumorRepository.deleteById(id);

    }
}
