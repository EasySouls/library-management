package com.tarjanyicsanad.domain.exceptions;

import com.tarjanyicsanad.domain.model.Book;

/**
 * An exception that is thrown when a {@link Book} is not found in the repository.
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Creates a new {@link BookNotFoundException}.
     * @param message the exception message
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
