package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.model.Book;

import java.util.List;

public interface BookRepository {
    void addBook(Book book);
    void removeBook(int id);
    Book getBook(int id);
    List<Book> findAll();
    void updateBook(Book book);
}
