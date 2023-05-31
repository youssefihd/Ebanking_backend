package com.example.ebankinbackend.exeption;

import org.springframework.aot.hint.ExecutableHint;

public class BalanceNotSuffisantException extends Exception{
    public BalanceNotSuffisantException(String balancenotsuffisant)
    {
        super(balancenotsuffisant);
    }
}
