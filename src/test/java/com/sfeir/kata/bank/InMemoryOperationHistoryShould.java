package com.sfeir.kata.bank;

import com.sfeir.kata.bank.operation.history.InMemoryOperationHistory;
import com.sfeir.kata.bank.operation.Operation;
import com.sfeir.kata.bank.operation.OperationType;
import com.sfeir.kata.bank.statement.StatementLine;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class InMemoryOperationHistoryShould {

    private static final LocalDateTime MOCK_DATE = LocalDateTime.of(2019, 3, 15, 0, 0);

    private InMemoryOperationHistory inMemoryOperationHistory;

    @Before
    public void setUp() {
        SystemDate systemDate = mock(SystemDate.class);
        when(systemDate.now()).thenReturn(MOCK_DATE);
        inMemoryOperationHistory = new InMemoryOperationHistory(systemDate);
    }

    @Test
    public void add_and_store_client_operation_in_history() {
        // GIVEN
        Money amount = new Money(BigDecimal.valueOf(100));
        // WHEN
        inMemoryOperationHistory.add(OperationType.DEPOSIT, amount);
        // THEN
        Assertions.assertThat(inMemoryOperationHistory.contains(new Operation(amount, MOCK_DATE, OperationType.DEPOSIT))).isTrue();
    }

    @Test
    public void generate_a_statement_from_operations_history() {
        // GIVEN
        SystemDate systemDate = mock(SystemDate.class);
        when(systemDate.now()).thenReturn(LocalDateTime.of(2019, 3, 7, 0, 0)).thenReturn(LocalDateTime.of(2019, 3, 8, 0, 0));
        inMemoryOperationHistory = new InMemoryOperationHistory(systemDate);

        inMemoryOperationHistory.add(OperationType.DEPOSIT, new Money(BigDecimal.valueOf(100)));
        inMemoryOperationHistory.add(OperationType.WITHDRAW, new Money(BigDecimal.valueOf(50).negate()));

        List<StatementLine> expectedStatement = new ArrayList<StatementLine>();
        expectedStatement.add(new StatementLine(new Operation(new Money(BigDecimal.valueOf(50).negate()), LocalDateTime.of(2019, 3, 8, 0, 0), OperationType.WITHDRAW), new Money(BigDecimal.valueOf(50))));
        expectedStatement.add(new StatementLine(new Operation(new Money(BigDecimal.valueOf(100)), LocalDateTime.of(2019, 3, 7, 0, 0), OperationType.DEPOSIT), new Money(BigDecimal.valueOf(100))));

        // WHEN
        List<StatementLine> result = inMemoryOperationHistory.getStatement();

        // THEN
        result.get(0).equals(expectedStatement.get(0));

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).isEqualTo(expectedStatement.get(0));
        Assertions.assertThat(result.get(1)).isEqualTo(expectedStatement.get(1));
    }


}