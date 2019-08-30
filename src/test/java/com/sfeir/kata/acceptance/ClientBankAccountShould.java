package com.sfeir.kata.acceptance;

import com.sfeir.kata.bank.*;
import com.sfeir.kata.bank.account.ClientAccount;
import com.sfeir.kata.bank.operation.history.InMemoryOperationHistory;
import com.sfeir.kata.bank.statement.ConsoleStatementPrinter;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientBankAccountShould {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final LocalDateTime MOCK_DATE = LocalDateTime.of(2019,04,8,0,0);

    private ClientAccount clientAccount;

    private StringBuilder expectedResult;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        SystemDate systemDate = mock(SystemDate.class);
        when(systemDate.now()).thenReturn(MOCK_DATE);
        clientAccount = new ClientAccount(new InMemoryOperationHistory(systemDate), new ConsoleStatementPrinter());
        System.setOut(new PrintStream(outContent));
        expectedResult = new StringBuilder();
        expectedResult.append("OPERATION | DATE | AMOUNT | BALANCE" + LINE_SEPARATOR);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void have_balance_increased_from_zero_to_100_when_client_deposit_100() {
        // GIVEN
        expectedResult.append("DEPOSIT | 08/04/2019 | 100 | 100" + LINE_SEPARATOR);

        // WHEN
        clientAccount.deposit(new Money(BigDecimal.valueOf(100)));
        clientAccount.printStatement();

        // THEN
        Assertions.assertThat(outContent.toString()).isEqualTo(expectedResult.toString());
    }

    @Test
    public void have_balance_decreased_from_100_to_50_when_client_withdraw_50() {
        // GIVEN
        clientAccount.deposit(new Money(BigDecimal.valueOf(100)));
        expectedResult.append("WITHDRAW | 08/04/2019 | -50 | 50" + LINE_SEPARATOR);
        expectedResult.append("DEPOSIT | 08/04/2019 | 100 | 100" +LINE_SEPARATOR);

        // WHEN
        clientAccount.withdraw(new Money(BigDecimal.valueOf(50)));
        clientAccount.printStatement();

        // THEN
        Assertions.assertThat(outContent.toString()).isEqualTo(expectedResult.toString());
    }
}
