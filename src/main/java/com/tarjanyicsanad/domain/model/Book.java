package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public record Book(
        int id,
        String title,
        String description,
        Author author,
        Collection<Loan> loans,
        LocalDate publishingDate
) implements Serializable {
    public BookEntity toEntityShallow() {
        // Convert only basic fields, avoid loans and deeply converting author
        return new BookEntity(title, description, author.toEntityShallow(), Collections.emptySet(), publishingDate);
    }

    public BookEntity toEntity() {
        // Convert loans fully but avoid recursive author conversion
        Collection<LoanEntity> loanEntities = loans.stream()
                .map(Loan::toEntity)
                .collect(Collectors.toSet());
        return new BookEntity(title, description, author.toEntityShallow(), loanEntities, publishingDate);
    }

    public static Book fromEntity(BookEntity entity) {
        // Convert author shallowly to avoid recursion
        Author author = Author.fromEntityShallow(entity.getAuthor());
        Collection<Loan> loans = entity.getLoan().stream()
                .map(Loan::fromEntity)
                .collect(Collectors.toSet());
        return new Book(entity.getId(), entity.getTitle(), entity.getDescription(), author, loans, entity.getPublishingDate());
    }

    public static Book fromEntityShallow(BookEntity entity) {
        // Create a Book without converting loans or deeply converting author
        Author author = Author.fromEntityShallow(entity.getAuthor());
        return new Book(entity.getId(), entity.getTitle(), entity.getDescription(), author, Collections.emptySet(), entity.getPublishingDate());
    }
}
