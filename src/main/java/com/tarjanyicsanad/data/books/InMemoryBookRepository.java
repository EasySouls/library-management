package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An in-memory implementation of the {@link BookRepository} interface.
 */
public class InMemoryBookRepository implements BookRepository {
    private final ArrayList<Book> books;

    MemberRepository memberRepository;
    LoanRepository loanRepository;

    @Inject
    public InMemoryBookRepository(List<Book> books, MemberRepository memberRepository, LoanRepository loanRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.books = books.isEmpty() ? new ArrayList<>() : new ArrayList<>(books);
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
    public Book findBookByTitle(String title) throws BookNotFoundException {
        return books.stream()
                .filter(book -> book.title().equals(title))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book with title " + title + " not found"));
    }

    @Override
    public List<Book> findAllBooks() {
        return books;
    }

    @Override
    public void addLoanToBook(int bookId, String memberEmail, LocalDate returnDate) throws IllegalArgumentException {
        Book book = getBook(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));
        Member member = memberRepository.findMemberByEmail(memberEmail);
        Loan loan = new Loan(0, book, member, LocalDate.now(), returnDate);
        loanRepository.addLoan(loan);
        book.loans().add(loan);
    }

    @Override
    public void updateBook(Book book) {
        removeBook(book.id());
        addBook(book);
    }
}
