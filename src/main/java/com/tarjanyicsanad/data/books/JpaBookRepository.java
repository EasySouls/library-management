package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import jakarta.persistence.EntityManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

public class JpaBookRepository
        extends BaseRepository<BookEntity, Integer, BookNotFoundException>
        implements BookRepository {
    @Inject
    public JpaBookRepository(EntityManager entityManager) {
        super(entityManager, BookEntity.class);
    }

    @Override
    public void addBook(Book book) {
        super.save(book.toEntity());
    }

    @Override
    public Optional<Book> getBook(int id) {
        return Optional.ofNullable(super.findById(id)).map(Book::fromEntity);
    }

    @Override
    public ArrayList<Book> findAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        super.findAll().forEach(bookEntity -> books.add(Book.fromEntity(bookEntity)));
        return books;
    }

    @Override
    public void removeBook(int id) throws BookNotFoundException {
        BookEntity bookEntity = super.findById(id);
        if (bookEntity == null) {
            throw new BookNotFoundException("Book with " + id + " not found");
        }
        super.delete(bookEntity);
    }

    @Override
    public void updateBook(Book book) {
        super.update(book.toEntity());
    }
}
