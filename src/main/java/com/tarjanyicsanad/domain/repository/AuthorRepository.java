package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    /**
     * Adds the given author to the repository.
     */
    void addAuthor(Author author);

    /**
     * Removes the author with the given id.
     * @throws AuthorNotFoundException if no author with the given id is found
     */
    void removeAuthor(int id) throws AuthorNotFoundException;

    /**
     * Returns an author with the given id.
     * @return an empty optional if no author with the given id is found
     */
    Optional<Author> getAuthor(int id);

    /**
     * Returns a list of all authors.
     * The list is unmodifiable, meaning that changes to it will not be reflected in the data.
     */
    List<Author> findAllAuthors();

    /**
     * Updates the author with the same id as the given author.
     * @throws AuthorNotFoundException if no author with the same id is found
     */
    void updateAuthor(Author author) throws AuthorNotFoundException;
}
