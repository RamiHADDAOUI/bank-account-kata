package com.sfeir.kata.bank.operation;

import com.sfeir.kata.bank.Money;

import java.time.LocalDateTime;
import java.util.Objects;

public class Operation {

    private final Money amount;

    private final LocalDateTime date;

    private final OperationType operationType;

    public Operation(Money amount, LocalDateTime date, OperationType operationType) {
        this.amount = amount;
        this.date = date;
        this.operationType = operationType;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return amount.equals(operation.amount) &&
                date.equals(operation.date) &&
                operationType == operation.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date, operationType);
    }
}
