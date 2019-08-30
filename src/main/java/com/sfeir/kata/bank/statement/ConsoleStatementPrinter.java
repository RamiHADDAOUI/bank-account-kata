package com.sfeir.kata.bank.statement;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsoleStatementPrinter implements StatementPrinter {

    private static final String FIELD_SEPARATOR = " | ";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void print(List<StatementLine> statement) {

        if (statement.isEmpty()) {
            return;
        }
        printColumnsHeader();

        for (StatementLine statementLine : statement) {
            printStatementLine(statementLine);
        }
    }

    private void printStatementLine(StatementLine statementLine) {
        System.out.println(statementLine.getOperationType().name()
                + FIELD_SEPARATOR
                + dateFormatter.format(statementLine.getDate())
                + FIELD_SEPARATOR
                + statementLine.getAmount()
                + FIELD_SEPARATOR
                + statementLine.getBalance()
        );
    }

    private void printColumnsHeader() {
        System.out.println("OPERATION | DATE | AMOUNT | BALANCE");
    }
}
