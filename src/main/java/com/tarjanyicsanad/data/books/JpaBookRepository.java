package com.tarjanyicsanad.data.books;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

/**
 * JPA implementation of the {@link BookRepository}.
 */
public class JpaBookRepository
        extends BaseRepository<BookEntity, Integer, BookNotFoundException>
        implements BookRepository {

    private final AuthorRepository authorRepository;

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
