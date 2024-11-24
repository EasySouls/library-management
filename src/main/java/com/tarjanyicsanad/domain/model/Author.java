package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
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

    public AuthorEntity toEntity() {
        Set<BookEntity> bookEntities = books.stream()
                .map(Book::toEntity)
                .collect(Collectors.toSet());
        return new AuthorEntity(firstName, lastName, dateOfBirth, bookEntities);
    }

    public static Author fromEntity(AuthorEntity entity) {
        Set<Book> books = entity.getBooks().stream()
                .map(Book::fromEntity)
                .collect(Collectors.toSet());
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), books);
    }
}
