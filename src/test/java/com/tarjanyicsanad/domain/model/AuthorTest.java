package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class AuthorTest {

    private Author author;

    @BeforeEach
    void setUp() {
        Set<Book> books = Set.of(
                new Book(1, "Book 1", "Description 1", null, Set.of(), LocalDate.now()),
                new Book(2, "Book 2", "Description 2", null, Set.of(), LocalDate.now())
        );
        author = new Author(1, "John", "Doe", LocalDate.of(1990, 1, 1), books);
    }

    @Test
    void fullName() {
        assertEquals("John Doe", author.fullName());
    }

    @Test
    void age() {
        assertEquals(LocalDate.now().getYear() - 1990, author.age());
    }

    @Test
    void toEntityShallow() {
        AuthorEntity entity = author.toEntity();

        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), entity.getBirthDate());
        assertEquals(0, entity.getBooks().size());
    }

    @Test
    void toEntity() {
        AuthorEntity entity = author.toEntity();

        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), entity.getBirthDate());
        assertEquals(2, entity.getBooks().size());
    }

    @Test
    void fromEntity() {
        AuthorEntity entity = new AuthorEntity(1, "John", "Doe", LocalDate.of(1990, 1, 1), Set.of());
        assertEquals(author, Author.fromEntity(entity));
    }

    @Test
    void fromEntityShallow() {
        AuthorEntity entity = new AuthorEntity("John", "Doe", LocalDate.of(1990, 1, 1), Set.of());
        assertEquals(author, Author.fromEntityShallow(entity));
    }
}