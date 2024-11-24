package com.tarjanyicsanad.data.authors;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

public class JpaAuthorRepository
        extends BaseRepository<AuthorEntity, Integer, AuthorNotFoundException>
        implements AuthorRepository {
    @Inject
    public JpaAuthorRepository(EntityManager entityManager) {
        super(entityManager, AuthorEntity.class);
    }

    @Override
    public void addAuthor(Author author) {
        super.save(author.toEntity());
    }

    @Override
    public Optional<Author> getAuthor(int id) {
        return Optional.ofNullable(super.findById(id)).map(Author::fromEntity);
    }

    @Override
    public AuthorEntity findOrCreateEntity(Author author) {
        Optional<AuthorEntity> existingEntity = findAuthorByName(author.firstName(), author.lastName());
        return existingEntity.orElseGet(() -> {
            AuthorEntity newEntity = author.toEntity();
            save(newEntity); // Save the new entity to the database
            return newEntity;
        });
    }

    @Override
    public Optional<AuthorEntity> findAuthorByName(String firstName, String lastName) {
        try {
            return Optional.of(entityManager.createQuery(
                            "SELECT a FROM authors a WHERE a.firstName = :firstName AND a.lastName = :lastName", AuthorEntity.class)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public ArrayList<Author> findAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        super.findAll().forEach(authorEntity -> authors.add(Author.fromEntity(authorEntity)));
        return authors;
    }

    @Override
    public void removeAuthor(int id) throws AuthorNotFoundException {
        AuthorEntity authorEntity = super.findById(id);
        if (authorEntity == null) {
            throw new AuthorNotFoundException("Author with " + id + " not found");
        }
        super.delete(authorEntity);
    }

    @Override
    public void updateAuthor(Author author) {
        super.update(author.toEntity());
    }
}
