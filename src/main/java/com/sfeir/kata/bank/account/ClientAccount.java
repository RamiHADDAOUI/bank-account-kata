package com.sfeir.kata.bank.account;

import com.sfeir.kata.bank.Money;
import com.sfeir.kata.bank.operation.history.OperationsHistory;
import com.sfeir.kata.bank.operation.OperationType;
import com.sfeir.kata.bank.statement.StatementPrinter;

public class ClientAccount implements Account {

    private final OperationsHistory operationsHistory;

    private final StatementPrinter statementPrinter;

    public ClientAccount(OperationsHistory operationsHistory, StatementPrinter statementPrinter) {
        this.operationsHistory = operationsHistory;
        this.statementPrinter = statementPrinter;
    }

    public void deposit(Money amount) {
        operationsHistory.add(OperationType.DEPOSIT, amount);
    }

    public void withdraw(Money amount) {
        operationsHistory.add(OperationType.WITHDRAW, amount.negate());
    }

    public void printStatement() {
        statementPrinter.print(operationsHistory.getStatement());
    }
}
