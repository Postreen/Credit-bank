package calculator.exceptions;

public class ScoringException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Loan was refused";
    public ScoringException() {
        super(DEFAULT_MESSAGE);
    }
}
