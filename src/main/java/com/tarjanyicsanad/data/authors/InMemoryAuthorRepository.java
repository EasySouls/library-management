package com.tarjanyicsanad.data.authors;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.repository.AuthorRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An in-memory implementation of the {@link AuthorRepository} interface.
 */
public class InMemoryAuthorRepository implements AuthorRepository {
    private final ArrayList<Author> authors;

    @Inject
    public InMemoryAuthorRepository(List<Author> authors) {
        this.authors = authors.isEmpty() ? new ArrayList<>() : new ArrayList<>(authors);
    }

    @Override
    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public void removeAuthor(int id) throws AuthorNotFoundException {
        boolean success = authors.removeIf(author -> author.id() == id);
        if (!success) {
            throw new AuthorNotFoundException("Author with id " + id + " not found");
        }
    }

    @Override
    public Optional<Author> getAuthor(int id) {
        Author author = authors.stream()
                .filter(a -> a.id() == id)
                .findFirst()
                .orElse(null);
        if (author == null) {
            return Optional.empty();
        }
        return Optional.of(author);
    }

    @Override
    public AuthorEntity findOrCreateEntity(Author author) {
        Optional<AuthorEntity> existingEntity = findAuthorByName(author.firstName(), author.lastName());
        return existingEntity.orElseGet(() -> {
            AuthorEntity newEntity = author.toEntity();
            addAuthor(author);
            return newEntity;
        });
    }

    @Override
    public Optional<AuthorEntity> findAuthorByName(String firstName, String lastName) {
        Author author = authors.stream()
                .filter(a -> a.firstName().equals(firstName) && a.lastName().equals(lastName))
                .findFirst()
                .orElse(null);
        if (author == null) {
            return Optional.empty();
        }
        return Optional.of(author.toEntity());
    }

    @Override
    public List<Author> findAllAuthors() {
        return authors;
    }

    @Override
    public void updateAuthor(Author author) throws AuthorNotFoundException {
        removeAuthor(author.id());
        addAuthor(author);
    }

    public void clear() {
        authors.clear();
    }

    public void addAll(List<Author> authors) {
        this.authors.addAll(authors);
    }
}
