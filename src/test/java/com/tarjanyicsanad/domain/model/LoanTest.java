package com.tarjanyicsanad.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    private Book book;
    private Member member;

    @BeforeEach
    void setUp() {
        book = new Book(1, "Title", "Description", null, Set.of(), LocalDate.of(2021, 1, 1));
        member = new Member(1, "Name", "Email", LocalDate.of(2021, 1, 1), Set.of());
    }

    @Test
    void isOverdue() {
        Loan loan = new Loan(1, book, member, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1));
        assertTrue(loan.isOverdue());
    }

    @Test
    void isReturned() {
        Loan loan = new Loan(1, book, member, LocalDate.now().minusDays(3), LocalDate.now().minusDays(2));
        assertTrue(loan.isReturned());
    }

    @Test
    void invalidParameterThrowsException() {
        Loan loan = new Loan(1, book, member, LocalDate.now().minusDays(3), null);
        assertThrows(NullPointerException.class, loan::isReturned);
    }

    @Test
    void returnDateBeforeLoanDateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Loan(1, book, member, LocalDate.now(), LocalDate.now().minusDays(1)));
    }

}