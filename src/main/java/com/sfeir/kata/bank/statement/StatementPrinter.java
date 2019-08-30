package com.sfeir.kata.bank.statement;

import java.util.List;

public interface StatementPrinter {

    void print(List<StatementLine> statement);
}
