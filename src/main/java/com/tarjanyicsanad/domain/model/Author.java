package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

public record Author(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Collection<Book> books
) implements Serializable {
    public String fullName() {
        return firstName + " " + lastName;
    }

    public int age() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public AuthorEntity toEntity() {
        Collection<BookEntity> bookEntities = books.stream()
                .map(Book::toEntity)
                .toList();
        return new AuthorEntity(id, firstName, lastName, dateOfBirth, bookEntities);
    }

    public static Author fromEntity(AuthorEntity entity) {
        Collection<Book> books = entity.getBooks().stream()
                .map(Book::fromEntity)
                .toList();
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), books);
    }
}
