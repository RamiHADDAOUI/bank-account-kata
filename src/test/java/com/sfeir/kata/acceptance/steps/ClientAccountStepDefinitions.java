package com.sfeir.kata.acceptance.steps;

import com.sfeir.kata.bank.Money;
import com.sfeir.kata.bank.SystemDate;
import com.sfeir.kata.bank.account.Account;
import com.sfeir.kata.bank.account.ClientAccount;
import com.sfeir.kata.bank.operation.history.InMemoryOperationHistory;
import com.sfeir.kata.bank.operation.history.OperationsHistory;
import com.sfeir.kata.bank.statement.ConsoleStatementPrinter;
import com.sfeir.kata.bank.statement.StatementPrinter;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

public class ClientAccountStepDefinitions {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Account account;

    @Before
    public void setUp() {
        OperationsHistory operationsHistory = new InMemoryOperationHistory(new SystemDate());
        StatementPrinter statementPrinter = new ConsoleStatementPrinter();
        account = new ClientAccount(operationsHistory, statementPrinter);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Given("^I deposit (\\d+) euros$")
    public void i_deposit(BigDecimal depositAmount) {
        account.deposit(new Money(depositAmount));
    }

    @Given("^I withdraw (\\d+) euros$")
    public void i_withdraw(BigDecimal withdrawAmount) {
        account.withdraw(new Money(withdrawAmount));
    }

    @When("^I ask the statement$")
    public void i_ask_the_statement() {
        outContent.reset();
        account.printStatement();
    }

    @Then("^My balance should be (-?\\d+)")
    public void my_balance_should_be(BigDecimal expectedBalance) {
        BigDecimal result = new BigDecimal(outContent.toString().split(LINE_SEPARATOR)[1].split("\\|")[3].trim()).setScale(1);
        Assertions.assertThat(result).isEqualTo(expectedBalance);
    }
}
