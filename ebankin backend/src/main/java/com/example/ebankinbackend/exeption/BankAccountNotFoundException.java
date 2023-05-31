package com.example.ebankinbackend.exeption;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String Customernotfound)
    {
        super(Customernotfound);
    }
}
