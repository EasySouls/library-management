package com.tarjanyicsanad.data.authors;

import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.repository.AuthorRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class InMemoryAuthorRepository implements AuthorRepository {
    private final List<Author> authors;

    @Inject
    public InMemoryAuthorRepository(List<Author> authors) {
        this.authors = authors;
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
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findAll() {
        return List.copyOf(authors);
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
