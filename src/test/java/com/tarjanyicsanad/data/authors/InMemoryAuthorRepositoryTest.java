package com.tarjanyicsanad.data.authors;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAuthorRepositoryTest {

    Set<Author> authors;
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authors = Set.of(
                new Author(1, "John", "Doe", LocalDate.of(1980, 1, 1), Set.of()),
                new Author(2, "Jane", "Doe", LocalDate.of(1985, 1, 1), Set.of()),
                new Author(3, "John", "Smith", LocalDate.of(1990, 1, 1), Set.of())
        );
        authorRepository = new InMemoryAuthorRepository(authors.stream().toList());
    }

    @Test
    void addAuthor() {
        Author newAuthor = new Author(4, "Alice", "Smith", LocalDate.of(1995, 1, 1), Set.of());

        authorRepository.addAuthor(newAuthor);
        List<Author> allAuthors = authorRepository.findAllAuthors();

        assertTrue(allAuthors.contains(newAuthor));
        assertEquals(4, allAuthors.size());
        assertEquals(newAuthor, authorRepository.getAuthor(4).get());
    }

    @Test
    void removeAuthor() {
        Author authorToRemove = authors.stream().toList().getFirst();

        authorRepository.removeAuthor(authorToRemove.id());
        List<Author> allAuthors = authorRepository.findAllAuthors();

        assertFalse(allAuthors.contains(authorToRemove));
        assertEquals(2, allAuthors.size());
        assertEquals(Optional.empty(), authorRepository.getAuthor(authorToRemove.id()));

        Integer id = authorToRemove.id();
        assertThrows(AuthorNotFoundException.class, () -> authorRepository.removeAuthor(id));
    }

    @Test
    void getAuthor() {
        Author author = authors.stream().toList().get(0);

        assertEquals(author, authorRepository.getAuthor(author.id()).get());
    }

    @Test
    void findOrCreateEntity_findsEntity() {
        Author author = authors.stream().toList().getFirst();
        AuthorEntity authorEntity = authorRepository.findOrCreateEntity(author);

        assertEquals(author.firstName(), authorEntity.getFirstName());
        assertEquals(author.lastName(), authorEntity.getLastName());
        assertEquals(author.dateOfBirth(), authorEntity.getBirthDate());
    }

    @Test
    void findOrCreateEntity_createsEntity() {
        Author newAuthor = new Author(4, "Alice", "Smith", LocalDate.of(1995, 1, 1), Set.of());
        authorRepository.findOrCreateEntity(newAuthor);

        List<Author> allAuthors = authorRepository.findAllAuthors();
        assertEquals(newAuthor, allAuthors.getLast());
    }


    @Test
    void findAuthorByName() {
        Optional<AuthorEntity> authorEntity = authorRepository.findAuthorByName("John", "Doe");
        assertTrue(authorEntity.isPresent());
        assertEquals("John", authorEntity.get().getFirstName());
        assertEquals("Doe", authorEntity.get().getLastName());

        Optional<AuthorEntity> nonExistentAuthor = authorRepository.findAuthorByName("Non", "Existent");
        assertFalse(nonExistentAuthor.isPresent());
    }

    @Test
    void findAllAuthors() {
        List<Author> allAuthors = authorRepository.findAllAuthors();
        assertEquals(authors.size(), allAuthors.size());
        assertTrue(allAuthors.containsAll(authors));
    }

    @Test
    void updateAuthor() {
        Author updatedAuthor = new Author(1, "John", "Doe", LocalDate.of(1980, 1, 1), Set.of());

        authorRepository.updateAuthor(updatedAuthor);
        List<Author> allAuthors = authorRepository.findAllAuthors();

        assertTrue(allAuthors.contains(updatedAuthor));
        assertEquals(3, allAuthors.size());
        assertEquals(updatedAuthor, authorRepository.getAuthor(1).get());
    }

    @Test
    void clear() {
        InMemoryAuthorRepository repo = (InMemoryAuthorRepository) authorRepository;
        repo.clear();
        List<Author> allAuthors = authorRepository.findAllAuthors();
        assertTrue(allAuthors.isEmpty());
    }

    @Test
    void addAll() {
        List<Author> newAuthors = List.of(
                new Author(4, "Alice", "Smith", LocalDate.of(1995, 1, 1), Set.of()),
                new Author(5, "Bob", "Smith", LocalDate.of(2000, 1, 1), Set.of())
        );

        InMemoryAuthorRepository repo = (InMemoryAuthorRepository) authorRepository;
        repo.addAll(newAuthors);
        List<Author> allAuthors = authorRepository.findAllAuthors();

        assertTrue(allAuthors.containsAll(newAuthors));
        assertEquals(5, allAuthors.size());
        assertEquals(newAuthors.get(0), authorRepository.getAuthor(4).get());
        assertEquals(newAuthors.get(1), authorRepository.getAuthor(5).get());
    }
}