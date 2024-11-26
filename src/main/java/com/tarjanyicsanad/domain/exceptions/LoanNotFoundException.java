package com.tarjanyicsanad.domain.exceptions;

import com.tarjanyicsanad.domain.model.Loan;

/**
 * An exception that is thrown when a {@link Loan} is not found in the repository.
 */
public class LoanNotFoundException extends RuntimeException {

    /**
     * Creates a new {@link LoanNotFoundException} with the given message.
     *
     * @param message the message
     */
    public LoanNotFoundException(String message) {
        super(message);
    }
}
