package com.tarjanyicsanad.domain.exceptions;

import com.tarjanyicsanad.domain.model.Author;

/**
 * An exception that is thrown when an {@link Author} is not found in the repository.
 */
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
