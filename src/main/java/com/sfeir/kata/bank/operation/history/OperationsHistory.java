package com.sfeir.kata.bank.operation.history;

import com.sfeir.kata.bank.Money;
import com.sfeir.kata.bank.operation.Operation;
import com.sfeir.kata.bank.operation.OperationType;
import com.sfeir.kata.bank.statement.StatementLine;

import java.util.List;

public interface OperationsHistory {

    void add(OperationType operationType, Money amount);

    Boolean contains(Operation operation);

    List<StatementLine> getStatement();
}
