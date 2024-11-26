package com.tarjanyicsanad.domain.exceptions;

import com.tarjanyicsanad.domain.model.Author;

/**
 * An exception that is thrown when an {@link Author} is not found in the repository.
 */
public class AuthorNotFoundException extends RuntimeException {
    /**
     * Creates a new {@link AuthorNotFoundException} with the given message.
     *
     * @param message the message of the exception
     */
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
