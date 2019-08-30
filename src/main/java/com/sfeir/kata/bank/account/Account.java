package com.sfeir.kata.bank.account;

import com.sfeir.kata.bank.Money;

public interface Account {

    void deposit(Money amount);

    void withdraw(Money amount);

    void printStatement();
}
