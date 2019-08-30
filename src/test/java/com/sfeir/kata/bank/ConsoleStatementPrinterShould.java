package com.sfeir.kata.bank;

import com.sfeir.kata.bank.operation.Operation;
import com.sfeir.kata.bank.operation.OperationType;
import com.sfeir.kata.bank.statement.ConsoleStatementPrinter;
import com.sfeir.kata.bank.statement.StatementLine;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ConsoleStatementPrinterShould {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ConsoleStatementPrinter consoleStatementPrinter;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        consoleStatementPrinter = new ConsoleStatementPrinter();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void print_statement_in_console() {
        // GIVEN
        List<StatementLine> statement = new ArrayList<StatementLine>();
        statement.add(new StatementLine(new Operation(new Money(BigDecimal.valueOf(50).negate()), LocalDateTime.of(2019,4,8,0,0), OperationType.WITHDRAW), new Money(BigDecimal.valueOf(50))));
        statement.add(new StatementLine(new Operation(new Money(BigDecimal.valueOf(100)), LocalDateTime.of(2019,4,7,0,0), OperationType.DEPOSIT), new Money(BigDecimal.valueOf(100))));

        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("OPERATION | DATE | AMOUNT | BALANCE" + LINE_SEPARATOR);
        expectedResult.append("WITHDRAW | 08/04/2019 | -50 | 50" + LINE_SEPARATOR);
        expectedResult.append("DEPOSIT | 07/04/2019 | 100 | 100" + LINE_SEPARATOR);

        // WHEN
        consoleStatementPrinter.print(statement);

        // THEN
        Assertions.assertThat(outContent.toString()).isEqualTo(expectedResult.toString());
    }

}