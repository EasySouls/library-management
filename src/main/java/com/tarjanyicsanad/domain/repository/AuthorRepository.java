package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * A repository for managing {@link Author}s.
 */
public interface AuthorRepository {

    /**
     * Adds the given author to the repository.
     * @param author the author to add
     */
    void addAuthor(Author author);

    /**
     * Removes the author with the given id.
     * @param id the id of the author to remove
     * @throws AuthorNotFoundException if no author with the given id is found
     */
    void removeAuthor(int id) throws AuthorNotFoundException;

    /**
     * Returns an author with the given id.
     * @param id the id of the author to find
     * @return an empty optional if no author with the given id is found
     */
    Optional<Author> getAuthor(int id);

    /**
     * Finds an existing author entity by the given author details or creates a new one if not found.
     * @param author the author details to find or create
     * @return the found or newly created AuthorEntity
     */
    AuthorEntity findOrCreateEntity(Author author);

    /**
     * Finds an author entity by the given first name and last name.
     * @param firstName the first name of the author
     * @param lastName the last name of the author
     * @return an optional containing the found AuthorEntity, or empty if not found
     */
    Optional<AuthorEntity> findAuthorByName(String firstName, String lastName);

    /**
     * Returns a list of all authors.
     * The list is unmodifiable, meaning that changes to it will not be reflected in the data.
     * @return a list of all authors
     */
    List<Author> findAllAuthors();

    /**
     * Updates the author with the same id as the given author.
     * @param author the author to update
     * @throws AuthorNotFoundException if no author with the same id is found
     */
    void updateAuthor(Author author) throws AuthorNotFoundException;
}
