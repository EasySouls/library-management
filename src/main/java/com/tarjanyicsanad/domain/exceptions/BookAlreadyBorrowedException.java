package com.tarjanyicsanad.domain.exceptions;

import com.tarjanyicsanad.domain.model.Book;

/**
 * Exception thrown when a {@link Book} is already borrowed.
 */
public class BookAlreadyBorrowedException extends RuntimeException {

    /**
     * Creates a new {@link BookAlreadyBorrowedException}.
     * @param message the exception message
     */
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
