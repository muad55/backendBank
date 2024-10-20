package com.example.Digital_Banking.Exceptions;

public class BalanceNotEnoughException extends Exception {
    public BalanceNotEnoughException(String balanceNotEnough) {
        super(balanceNotEnough);
    }
}
