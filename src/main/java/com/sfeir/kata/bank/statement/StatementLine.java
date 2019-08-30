package com.sfeir.kata.bank.statement;

import com.sfeir.kata.bank.Money;
import com.sfeir.kata.bank.operation.Operation;
import com.sfeir.kata.bank.operation.OperationType;

import java.time.LocalDateTime;
import java.util.Objects;

public class StatementLine {

    private final Operation operation;

    private final Money balance;

    public StatementLine(Operation operation, Money balance) {
        this.operation = operation;
        this.balance = balance;
    }


    public Money getAmount() {
        return operation.getAmount();
    }

    public LocalDateTime getDate() {
        return operation.getDate();
    }

    public OperationType getOperationType() {
        return operation.getOperationType();
    }

    public Money getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatementLine that = (StatementLine) o;
        return operation.equals(that.operation) &&
                balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, balance);
    }
}
