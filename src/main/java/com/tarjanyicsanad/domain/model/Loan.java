package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.loans.entities.LoanEntity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a loan of a book to a member with details about the loan and return dates.
 */
public record Loan(
        Integer id,
        Book book,
        Member member,
        LocalDate loanedAt,
        LocalDate returnDate
) implements Serializable {

    /**
     * Constructs a Loan record and ensures the return date is after the loan date.
     *
     * @param id         the unique identifier of the loan.
     * @param book       the book being loaned.
     * @param member     the member who loaned the book.
     * @param loanedAt   the date the book was loaned.
     * @param returnDate the date the book is expected to be returned.
     * @throws IllegalArgumentException if the return date is before the loan date.
     */
    public Loan {
        if (loanedAt.isAfter(returnDate)) {
            throw new IllegalArgumentException("Return date must be after loan date");
        }
    }

    /**
     * Checks if the loan is overdue based on the current date.
     *
     * @return true if the loan is overdue, false otherwise.
     */
    public boolean isOverdue() {
        return LocalDate.now().isAfter(returnDate);
    }

    /**
     * Checks if the book has been returned based on the current date.
     *
     * @return true if the book has been returned, false otherwise.
     */
    public boolean isReturned() {
        return LocalDate.now().isAfter(returnDate);
    }

    /**
     * Checks if the book is currently loaned out based on the current date.
     *
     * @return true if the book is still loaned out, false otherwise.
     */
    public boolean isLoaned() {
        return LocalDate.now().isBefore(returnDate);
    }

    /**
     * Converts this Loan to a LoanEntity for persistence without the member.
     *
     * @return a LoanEntity representing this Loan.
     */
    public LoanEntity toEntityShallow() {
        return new LoanEntity(book.toEntity(), null, loanedAt, returnDate);
    }

    /**
     * Converts this Loan to a LoanEntity for persistence.
     *
     * @return a LoanEntity representing this Loan.
     */
    public LoanEntity toEntity() {
        return new LoanEntity(book.toEntity(), member.toEntity(), loanedAt, returnDate);
    }

    /**
     * Creates a Loan from a LoanEntity.
     *
     * @param entity the LoanEntity to convert.
     * @return a Loan representing the given LoanEntity.
     */
    public static Loan fromEntity(LoanEntity entity) {
        return new Loan(entity.getId(),
                Book.fromEntityShallow(entity.getBook()),
                Member.fromEntity(entity.getMember()),
                entity.getLoanedAt(),
                entity.getReturnDate());
    }

    /**
     * Creates a Loan from a LoanEntity without the member.
     *
     * @param entity the LoanEntity to convert.
     * @return a Loan representing the given LoanEntity.
     */
    public static Loan fromEntityShallow(LoanEntity entity) {
        return new Loan(entity.getId(),
                Book.fromEntityShallow(entity.getBook()),
                Member.fromEntityShallow(entity.getMember()),
                entity.getLoanedAt(),
                entity.getReturnDate());
    }
}
