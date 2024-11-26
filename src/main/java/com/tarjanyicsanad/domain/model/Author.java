package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents an author with basic details and a set of books.
 * @param id the unique identifier of the author.
 * @param firstName the first name of the author.
 * @param lastName the last name of the author.
 * @param dateOfBirth the date of birth of the author.
 * @param books the set of books written by the author.
 * @see Book
 */
public record Author(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Set<Book> books
) implements Serializable {

    /**
     * Returns the full name of the author, which is the concatenation of the first name and last name.
     * @return the full name of the author.
     */
    public String fullName() {
        return firstName + " " + lastName;
    }

    /**
     * Calculates the age of the author based on the current date.
     * @return the age of the author.
     */
    public int age() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    /**
     * Converts this author to a shallow AuthorEntity, excluding books.
     * @return a shallow AuthorEntity.
     */
    public AuthorEntity toEntityShallow() {
        // Convert only the basic fields, avoid books
        return new AuthorEntity(firstName, lastName, dateOfBirth, Collections.emptySet());
    }

    /**
     * Converts this author to an AuthorEntity, including books with shallow references.
     * @return an AuthorEntity with books.
     */
    public AuthorEntity toEntity() {
        // Convert books with shallow references to avoid recursion
        Set<BookEntity> bookEntities = books.stream()
                .map(Book::toEntityShallow)
                .collect(Collectors.toSet());
        return new AuthorEntity(firstName, lastName, dateOfBirth, bookEntities);
    }

    /**
     * Creates an Author from an AuthorEntity, including books with shallow references.
     *
     * @param entity the AuthorEntity to convert.
     * @return an Author with books.
     */
    public static Author fromEntity(AuthorEntity entity) {
        // Convert books shallowly to avoid recursion
        Set<Book> books = entity.getBooks().stream()
                .map(Book::fromEntityShallow)
                .collect(Collectors.toSet());
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), books);
    }

    /**
     * Creates an Author from an AuthorEntity, excluding books.
     *
     * @param entity the AuthorEntity to convert.
     * @return an Author without books.
     */
    public static Author fromEntityShallow(AuthorEntity entity) {
        // Create an Author without converting books
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), Collections.emptySet());
    }
}
