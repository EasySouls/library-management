package com.tarjanyicsanad.domain.exceptions;

import com.tarjanyicsanad.domain.model.Book;

/**
 * Exception thrown when a {@link Book} is already borrowed.
 */
public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
