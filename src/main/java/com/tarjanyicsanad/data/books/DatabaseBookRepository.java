package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.domain.model.Book;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseBookRepository implements BookRepository {
    private final SessionFactory sessionFactory;

    public DatabaseBookRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBook(Book book) {
        sessionFactory.inTransaction(session -> {
            BookEntity bookEntity = new BookEntity(book.id(), book.title(), book.description(), book.author());
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
    public Book getBook(int id) {
        AtomicReference<BookEntity> bookEntity = new AtomicReference<>();
        sessionFactory.inTransaction(session ->
            bookEntity.set(session.find(BookEntity.class, id))
        );
        return Book.fromEntity(bookEntity.get());
    }

    @Override
    public List<Book> findAll() {
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
