package com.example.ebankinbackend.mapers;

import com.example.ebankinbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service

public class Mappers {
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);


        return customerDTO;
    }

    public Customer fromcustomerDTO(CustomerDTO customerdto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerdto, customer);

        return customer;
    }
}
