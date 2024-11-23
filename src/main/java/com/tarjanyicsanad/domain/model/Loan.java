package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.loans.entities.LoanEntity;

import java.time.LocalDate;

public record Loan(
        Integer id,
        Book book,
        Member member,
        LocalDate loanedAt,
        LocalDate returnDate
) {
    public Loan {
        if (loanedAt.isAfter(returnDate)) {
            throw new IllegalArgumentException("Return date must be after loan date");
        }
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(returnDate);
    }

    public boolean isReturned() {
        return LocalDate.now().isAfter(returnDate);
    }

    public boolean isLoaned() {
        return LocalDate.now().isBefore(returnDate);
    }

    public LoanEntity toEntity() {
        return new LoanEntity(id, book.toEntity(), member.toEntity(), loanedAt, returnDate);
    }

    public static Loan fromEntity(LoanEntity entity) {
        return new Loan(entity.getId(),
                Book.fromEntity(entity.getBook()),
                Member.fromEntity(entity.getMember()),
                entity.getLoanedAt(),
                entity.getReturnDate());
    }
}
