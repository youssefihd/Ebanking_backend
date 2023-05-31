package com.example.ebankinbackend.web;

import com.example.ebankinbackend.entities.Customer;
import com.example.ebankinbackend.exeption.CustomernotfoundExeption;
import com.example.ebankinbackend.mapers.CustomerDTO;
import com.example.ebankinbackend.service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.listcustomers();
    }
    @GetMapping("/cusromers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long Customerid) throws CustomernotfoundExeption {
        return bankAccountService.getCustomer(Customerid);
    }
    @PostMapping("/customers/")
public CustomerDTO saveCostumer(@RequestBody CustomerDTO customerDTO) {
        bankAccountService.saveCustomer(CustomerDTO);
    }

        }
        @PutMapping("customers/{id}")
public void delete(Long id){
        bankAccountService.delete(id);

    }
}
