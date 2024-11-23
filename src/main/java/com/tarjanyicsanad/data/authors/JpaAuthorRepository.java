package com.tarjanyicsanad.data.authors;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import jakarta.persistence.EntityManager;

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
