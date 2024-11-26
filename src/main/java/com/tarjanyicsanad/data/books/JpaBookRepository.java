package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity;
import com.tarjanyicsanad.data.members.entities.MemberEntity;
import com.tarjanyicsanad.domain.exceptions.BookAlreadyBorrowedException;
import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * JPA implementation of the {@link BookRepository} interface.
 */
public class JpaBookRepository
        extends BaseRepository<BookEntity, Integer, BookNotFoundException>
        implements BookRepository {

    private final AuthorRepository authorRepository;

    private static final Logger logger = LogManager.getLogger(JpaBookRepository.class);

    /**
     * Creates a new {@link JpaBookRepository}.
     *
     * @param entityManager the JPA entity manager
     * @param authorRepository the repository to use for accessing authors
     */
    @Inject
    public JpaBookRepository(EntityManager entityManager, AuthorRepository authorRepository) {
        super(entityManager, BookEntity.class);
        this.authorRepository = authorRepository;
    }

    @Override
    public Book findBookByTitle(String title) throws BookNotFoundException {
        try {
            BookEntity entity = entityManager.createQuery(
                            "SELECT a FROM books a WHERE a.title = :title", BookEntity.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return Book.fromEntity(entity);
        } catch (NoResultException e) {
            throw new BookNotFoundException("Book with title " + title + " not found");
        }
    }

    @Override
    public void addBook(Book book) {
        AuthorEntity managedAuthor = authorRepository.findOrCreateEntity(book.author());
        BookEntity bookEntity = book.toEntity();
        bookEntity.setAuthor(managedAuthor);
        super.save(bookEntity);
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

    /**
     * Returns a list of all books that are currently loaned.
     *
     * @return a list of all loaned books
     */
    public List<Book> findAllLoanedBooks() {
        ArrayList<Book> books = new ArrayList<>();
        super.findAll().forEach(bookEntity -> {
            Set<LoanEntity> loans = bookEntity.getLoans();
            for (LoanEntity loan : loans) {
                if (loan.getReturnDate().isAfter(LocalDate.now())) {
                    books.add(Book.fromEntity(bookEntity));
                    break;
                }
            }
        });
        return books;
    }

    @Override
    public void removeBook(int id) throws BookNotFoundException {
        BookEntity bookEntity = super.findById(id);
        if (bookEntity == null) {
            throw new BookNotFoundException("Book with " + id + " not found");
        }
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("DELETE FROM books b WHERE b.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            // Delete all loans associated with the book
            entityManager.createQuery("DELETE FROM loans l WHERE l.book.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            logger.error("Error while removing book", e);
            throw e;
        }
    }

    @Override
    public void addLoanToBook(int bookId, String memberEmail, LocalDate returnDate) throws IllegalArgumentException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Fetch the book and member
            BookEntity book = entityManager.find(BookEntity.class, bookId);
            MemberEntity member = entityManager.createQuery(
                            "SELECT m FROM members m WHERE m.email = :email", MemberEntity.class)
                    .setParameter("email", memberEmail)
                    .getSingleResult();

            if (book == null || member == null) {
                throw new IllegalArgumentException("Book or Member not found");
            }

            // Check if the book is already borrowed by the member
            Set<LoanEntity> loans = book.getLoans();
            for (LoanEntity existingLoan : loans) {
                if (existingLoan.getMember() != null && existingLoan.getMember().equals(member)) {
                    throw new IllegalArgumentException("Book is already borrowed by this member");
                }
            }

            // Check if the book is already borrowed
            for (LoanEntity existingLoan : loans) {
                if (existingLoan.getReturnDate().isAfter(LocalDate.now())) {
                    throw new BookAlreadyBorrowedException("Book is already borrowed");
                }
            }

            // Create a new loan
            LoanEntity loan = new LoanEntity(book, member, LocalDate.now(), returnDate);
            loans.add(loan);
            book.setLoans(loans);

            // Persist changes
            entityManager.merge(book);
            entityManager.merge(loan);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    @Override
    public void updateBook(Book book) {
        super.update(book.toEntity());
    }
}
