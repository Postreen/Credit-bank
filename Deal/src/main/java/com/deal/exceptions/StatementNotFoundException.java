package com.deal.exceptions;

public class StatementNotFoundException extends RuntimeException {
    public StatementNotFoundException(String message) {
        super(message);
    }
}
