package com.tarjanyicsanad.domain.exceptions;

import com.tarjanyicsanad.domain.model.Member;

/**
 * An exception that is thrown when a {@link Member} is not found in the repository.
 */
public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
