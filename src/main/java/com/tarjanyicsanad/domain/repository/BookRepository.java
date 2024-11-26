package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * A repository for {@link Book}s.
 */
public interface BookRepository {
    /**
     * Add a book to the repository
     * @param book the book to add
     */
    void addBook(Book book);

    /**
     * Remove a book from the repository
     * @param id the id of the book to remove
     * @throws BookNotFoundException if the book with the given id is not found
     */
    void removeBook(int id) throws BookNotFoundException;

    /**
     * Returns a book with the given id.
     * @param id the id of the book to return
     * @return an empty optional if no book with the given id is found
     */
    Optional<Book> getBook(int id);

    /**
     * Returns a book with the given title.
     * @param title the title of the book to return
     * @return an empty optional if no book with the given title is found
     */
    Book findBookByTitle(String title) throws BookNotFoundException;

    /**
     * Get all books from the repository
     * @return a list of all books in the repository
     */
    List<Book> findAllBooks();

    /**
     * Adds a loan to the book with the given id.
     * @param bookId the id of the book to add the loan to
     * @param memberEmail the email of the member who borrowed the book
     * @param returnDate the date the book should be returned
     * @throws IllegalArgumentException if the parameters are invalid or the book is already borrowed
     */
    void addLoanToBook(int bookId, String memberEmail, LocalDate returnDate) throws IllegalArgumentException;

    /**
     * Updates the book with the same id as the given book.
     * @param book the book to update
     * @throws BookNotFoundException if no book with the same id is found
     */
    void updateBook(Book book) throws BookNotFoundException;
}
