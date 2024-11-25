package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a book with its details, author, and associated loans.
 */
public record Book(
        int id,
        String title,
        String description,
        Author author,
        Set<Loan> loans,
        LocalDate publishingDate
) implements Serializable {

    /**
     * Converts this book to a shallow BookEntity, excluding loans and deeply converting the author.
     *
     * @return a shallow BookEntity.
     */
    public BookEntity toEntityShallow() {
        // Convert only basic fields, avoid loans and deeply converting author
        return new BookEntity(title, description, author.toEntityShallow(), Collections.emptySet(), publishingDate);
    }

    /**
     * Converts this book to a BookEntity, including loans but avoiding recursive author conversion.
     *
     * @return a BookEntity with loans.
     */
    public BookEntity toEntity() {
        // Convert loans fully but avoid recursive author conversion
        Set<LoanEntity> loanEntities = loans.stream()
                .map(Loan::toEntityShallow)
                .collect(Collectors.toSet());

        BookEntity entity = new BookEntity(title, description, author.toEntityShallow(), loanEntities, publishingDate);
        entity.setId(id);
        return entity;
    }

    /**
     * Creates a Book from a BookEntity, including loans and shallowly converting the author.
     *
     * @param entity the BookEntity to convert.
     * @return a Book with loans.
     */
    public static Book fromEntity(BookEntity entity) {
        // Convert author shallowly to avoid recursion
        Author author = Author.fromEntityShallow(entity.getAuthor());
        Set<Loan> loans = entity.getLoans().stream()
                .map(Loan::fromEntityShallow)
                .collect(Collectors.toSet());
        return new Book(entity.getId(), entity.getTitle(), entity.getDescription(), author, loans, entity.getPublishingDate());
    }

    /**
     * Creates a Book from a BookEntity, excluding loans and deeply converting the author.
     *
     * @param entity the BookEntity to convert.
     * @return a Book without loans.
     */
    public static Book fromEntityShallow(BookEntity entity) {
        // Create a Book without converting loans or deeply converting author
//        Author author = Author.fromEntityShallow(entity.getAuthor());
//        Set<Loan> loans = entity.getLoans().stream()
//                .map(Loan::fromEntityShallow)
//                .collect(Collectors.toSet());
        return new Book(entity.getId(), entity.getTitle(), entity.getDescription(), null, null, entity.getPublishingDate());
    }
}
