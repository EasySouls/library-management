package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public record Book(
        int id,
        String title,
        String description,
        Author author,
        Collection<Loan> loans,
        LocalDate publishingDate
) implements Serializable {
    public BookEntity toEntity() {
        Collection<LoanEntity> loanEntities = loans.stream()
                .map(Loan::toEntity)
                .collect(Collectors.toSet());
        return new BookEntity(title, description, author.toEntity(), loanEntities, publishingDate);
    }

    public static Book fromEntity(BookEntity entity) {
        return new Book(entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                Author.fromEntity(entity.getAuthor()),
                entity.getLoan().stream()
                        .map(Loan::fromEntity)
                        .collect(Collectors.toSet()),
                entity.getPublishingDate());
    }
}
