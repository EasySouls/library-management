package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.data.loans.InMemoryLoanRepository;
import com.tarjanyicsanad.data.members.InMemoryMemberRepository;
import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryBookRepositoryTest {

    private BookRepository bookRepository;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository(List.of());
        LoanRepository loanRepository = new InMemoryLoanRepository(Set.of());
        Author author = new Author(1, "Author 1", "Author 1", LocalDate.of(1878, 1, 13), Set.of());
        List<Book> books = List.of(
                new Book(1, "Book 1", "Author 1", author, Set.of(), LocalDate.of(2021, 1, 1)),
                new Book(2, "Book 2", "Author 2", author, Set.of(), LocalDate.of(2021, 1, 1)),
                new Book(3, "Book 3", "Author 3", author, Set.of(), LocalDate.of(2021, 1, 1))
        );
        bookRepository = new InMemoryBookRepository(books, memberRepository, loanRepository);
    }

    @Test
    void addBook() {
        Author author = new Author(4, "Author 4", "Author 4", LocalDate.of(1980, 1, 1), Set.of());
        Book newBook = new Book(4, "Book 4", "Description 4", author, Set.of(), LocalDate.of(2022, 1, 1));
        bookRepository.addBook(newBook);
        List<Book> allBooks = bookRepository.findAllBooks();

        assertTrue(allBooks.contains(newBook));
        assertEquals(4, allBooks.size());
        assertEquals(newBook, bookRepository.getBook(4).get());
    }

    @Test
    void removeBook() {
        bookRepository.removeBook(1);
        List<Book> allBooks = bookRepository.findAllBooks();

        assertEquals(2, allBooks.size());
        assertFalse(allBooks.stream().anyMatch(book -> book.id() == 1));
    }

    @Test
    void getBook() {
        Optional<Book> book = bookRepository.getBook(1);
        assertTrue(book.isPresent());
        assertEquals("Book 1", book.get().title());
    }

    @Test
    void findBookByTitle() {
        Book book = bookRepository.findBookByTitle("Book 1");
        assertEquals("Book 1", book.title());
    }

    @Test
    void findBookByTitle_notFound() {
        assertThrows(BookNotFoundException.class, () -> bookRepository.findBookByTitle("Nonexistent Book"));
    }

    @Test
    void findAllBooks() {
        List<Book> allBooks = bookRepository.findAllBooks();
        assertEquals(3, allBooks.size());
    }

//    @Test
//    void addLoanToBook() {
//        Author author = new Author(4, "Author 4", "Author 4", LocalDate.of(1980, 1, 1), Set.of());
//        Book book = new Book(4, "Book 4", "Description 4", author, Set.of(), LocalDate.of(2022, 1, 1));
//        bookRepository.addBook(book);
//
//        Member member = new Member(1, "Member 1", "member1@example.com", LocalDate.of(2020, 1, 1), Set.of());
//        memberRepository.addMember(member);
//
//        bookRepository.addLoanToBook(4, "member1@example.com", LocalDate.of(2024, 12, 1));
//        Book updatedBook = bookRepository.getBook(4).get();
//
//        assertEquals(1, updatedBook.loans().size());
//        Loan loan = updatedBook.loans().iterator().next();
//        assertEquals("Member 1", loan.member().name());
//        assertEquals(LocalDate.of(2022, 2, 1), loan.returnDate());
//    }

    @Test
    void updateBook() {
        Author author = new Author(1, "Updated Author", "Updated Author", LocalDate.of(1878, 1, 13), Set.of());
        Book updatedBook = new Book(1, "Updated Book 1", "Updated Description 1", author, Set.of(), LocalDate.of(2021, 1, 1));
        bookRepository.updateBook(updatedBook);

        Book book = bookRepository.getBook(1).get();
        assertEquals("Updated Book 1", book.title());
        assertEquals("Updated Description 1", book.description());
    }
}