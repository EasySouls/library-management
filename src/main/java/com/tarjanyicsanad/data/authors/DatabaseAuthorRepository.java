package com.tarjanyicsanad.data.authors;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseAuthorRepository implements AuthorRepository {
    private final SessionFactory sessionFactory;

    @Inject
    public DatabaseAuthorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addAuthor(Author author) {
        sessionFactory.inTransaction(session -> {
            AuthorEntity authorEntity = new AuthorEntity(author.id(), author.firstName(), author.lastName(), author.dateOfBirth());
            session.persist(authorEntity);
        });
    }

    @Override
    public void removeAuthor(int id) {
        sessionFactory.inTransaction(session -> {
            AuthorEntity authorEntity = session.find(AuthorEntity.class, id);
            session.remove(authorEntity);
        });
    }

    @Override
    public Optional<Author> getAuthor(int id) {
        AtomicReference<AuthorEntity> authorEntity = new AtomicReference<>();
        sessionFactory.inTransaction(session ->
                authorEntity.set(session.find(AuthorEntity.class, id))
        );
        return Optional.ofNullable(authorEntity.get()).map(Author::fromEntity);
    }

    @Override
    public List<Author> findAll() {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        sessionFactory.inTransaction(session ->
                authorEntities.addAll(session.createQuery("SELECT a FROM authors a", AuthorEntity.class).getResultList())
        );
        return authorEntities.stream().map(Author::fromEntity).toList();
    }

    @Override
    public void updateAuthor(Author author) {
        sessionFactory.inTransaction(session -> {
            AuthorEntity authorEntity = author.toEntity();
            session.merge(authorEntity);
        });
    }
}
