package com.statement.exceptions;

import java.util.UUID;

public class StatementNotFoundException extends RuntimeException {
    public StatementNotFoundException(UUID uuid) {
        super("StatementId = " + uuid.toString() + " not found");
    }
}
