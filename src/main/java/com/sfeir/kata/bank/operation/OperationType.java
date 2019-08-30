package com.sfeir.kata.bank.operation;

public enum OperationType {

    DEPOSIT("deposit operation"),
    WITHDRAW("withdraw operation");

    private final String operationName;

    OperationType(String operationName) {
        this.operationName = operationName;
    }
}
