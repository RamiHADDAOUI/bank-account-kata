package com.sfeir.kata.bank.operation.history;

import com.sfeir.kata.bank.Money;
import com.sfeir.kata.bank.SystemDate;
import com.sfeir.kata.bank.operation.Operation;
import com.sfeir.kata.bank.operation.OperationType;
import com.sfeir.kata.bank.statement.StatementLine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryOperationHistory implements OperationsHistory {

    private final List<Operation> operations;

    private final SystemDate systemDate;

    public InMemoryOperationHistory(SystemDate systemDate) {
        this.systemDate = systemDate;
        this.operations = new ArrayList<>();
    }

    public void add(OperationType operationType, Money amount) {
        this.operations.add(new Operation(amount, this.systemDate.now(), operationType));
    }

    public Boolean contains(final Operation operation) {
        return operations.contains(operation);
    }

    public List<StatementLine> getStatement() {
        List<StatementLine> statement = new ArrayList<>();
        Money balance = new Money(BigDecimal.valueOf(0));

        for (Operation operation : this.operations) {
            balance = balance.add(operation.getAmount());
            statement.add(new StatementLine(operation, balance));
        }

        Collections.reverse(statement);

        return statement;
    }
}
