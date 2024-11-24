package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record Author(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Set<Book> books
) implements Serializable {
    public String fullName() {
        return firstName + " " + lastName;
    }

    public int age() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public AuthorEntity toEntityShallow() {
        // Convert only the basic fields, avoid books
        return new AuthorEntity(firstName, lastName, dateOfBirth, Collections.emptySet());
    }

    public AuthorEntity toEntity() {
        // Convert books with shallow references to avoid recursion
        Set<BookEntity> bookEntities = books.stream()
                .map(Book::toEntityShallow)
                .collect(Collectors.toSet());
        return new AuthorEntity(firstName, lastName, dateOfBirth, bookEntities);
    }

    public static Author fromEntity(AuthorEntity entity) {
        // Convert books shallowly to avoid recursion
        Set<Book> books = entity.getBooks().stream()
                .map(Book::fromEntityShallow)
                .collect(Collectors.toSet());
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), books);
    }

    public static Author fromEntityShallow(AuthorEntity entity) {
        // Create an Author without converting books
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), Collections.emptySet());
    }
}
