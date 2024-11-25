package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.domain.model.Book;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Hibernate implementation of the {@link BookRepository} interface.
 */
public class DatabaseBookRepository implements BookRepository {
    private final SessionFactory sessionFactory;

    @Inject
    public DatabaseBookRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBook(Book book) {
        sessionFactory.inTransaction(session -> {
            BookEntity bookEntity = book.toEntity();
            session.persist(bookEntity);
        });
    }

    @Override
    public void removeBook(int id) {
        sessionFactory.inTransaction(session -> {
            BookEntity bookEntity = session.find(BookEntity.class, id);
            session.remove(bookEntity);
        });
    }

    @Override
    public Optional<Book> getBook(int id) {
        AtomicReference<BookEntity> bookEntity = new AtomicReference<>();
        sessionFactory.inTransaction(session ->
            bookEntity.set(session.find(BookEntity.class, id))
        );
        return Optional.ofNullable(bookEntity.get()).map(Book::fromEntity);
    }

    @Override
    public Book findBookByTitle(String title) throws BookNotFoundException {
        AtomicReference<BookEntity> bookEntity = new AtomicReference<>();
        sessionFactory.inTransaction(session -> {
            try {
                bookEntity.set(session.createQuery("SELECT b FROM books b WHERE b.title = :title", BookEntity.class)
                        .setParameter("title", title)
                        .getSingleResult());
            } catch (Exception e) {
                throw new BookNotFoundException("Book not found with title: " + title);
            }
        });
        return Book.fromEntity(bookEntity.get());
    }

    @Override
    public List<Book> findAllBooks() {
        List<BookEntity> bookEntities = new ArrayList<>();
        sessionFactory.inTransaction(session ->
            bookEntities.addAll(session.createQuery("SELECT b FROM books b", BookEntity.class).getResultList())
        );
        return bookEntities.stream().map(Book::fromEntity).toList();
    }

    @Override
    public void updateBook(Book book) {
        sessionFactory.inTransaction(session -> {
            BookEntity bookEntity = book.toEntity();
            session.merge(bookEntity);
        });
    }
}
