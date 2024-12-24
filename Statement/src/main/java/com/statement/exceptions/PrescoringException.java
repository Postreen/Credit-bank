package com.statement.exceptions;

import com.statement.dto.LoanStatementRequestDto;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class PrescoringException extends RuntimeException {
    private Set<ConstraintViolation<LoanStatementRequestDto>> resultPrescoring;

    public PrescoringException(String s) {
        super(s);
    }
}