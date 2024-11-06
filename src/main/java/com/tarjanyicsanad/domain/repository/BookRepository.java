package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.model.Book;

public interface BookRepository {
    public void addBook(Book book);
    public void removeBook(int id);
    public Book getBook(int id);
    public void updateBook(Book book);
}
