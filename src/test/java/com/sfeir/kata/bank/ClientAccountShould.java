package com.sfeir.kata.bank;

import com.sfeir.kata.bank.account.ClientAccount;
import com.sfeir.kata.bank.operation.OperationType;
import com.sfeir.kata.bank.operation.history.OperationsHistory;
import com.sfeir.kata.bank.statement.StatementLine;
import com.sfeir.kata.bank.statement.StatementPrinter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ClientAccountShould {

    @Mock
    private OperationsHistory operationsHistory;

    @Mock
    private StatementPrinter statementPrinter;

    @InjectMocks
    private ClientAccount clientAccount;

    @Before
    public void setUp() {
    }

    @Test
    public void add_a_deposit_operation() {
        // GIVEN
        Money amount = new Money(BigDecimal.valueOf(100));
        // WHEN
        clientAccount.deposit(amount);
        // THEN
        verify(operationsHistory, times(1)).add(OperationType.DEPOSIT, amount);
    }


    @Test
    public void add_a_withdraw_operation() {
        // GIVEN
        Money amount = new Money(BigDecimal.valueOf(50));
        // WHEN
        clientAccount.withdraw(amount.negate());
        // THEN
        verify(operationsHistory, times(1)).add(OperationType.WITHDRAW, amount);
    }

    @Test
    public void print_statement_operations() {
        // GIVEN
        // WHEN
        clientAccount.printStatement();
        // THEN
        verify(operationsHistory, times(1)).getStatement();
        verify(statementPrinter, times(1)).print(anyListOf(StatementLine.class));
    }

}