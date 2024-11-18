package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.BookRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {
    private final List<Book> books;

    @Inject
    public InMemoryBookRepository(List<Book> books) {
        this.books = books;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void removeBook(int id) {
        boolean success = books.removeIf(book -> book.id() == id);
        if (!success) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
    }

    @Override
    public Optional<Book> getBook(int id) {
        return books.stream()
                .filter(book -> book.id() == id)
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return List.copyOf(books);
    }

    @Override
    public void updateBook(Book book) {
        removeBook(book.id());
        addBook(book);
    }
}
