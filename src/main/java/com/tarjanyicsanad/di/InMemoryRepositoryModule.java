package com.tarjanyicsanad.di;

import com.tarjanyicsanad.data.authors.InMemoryAuthorRepository;
import com.tarjanyicsanad.data.books.InMemoryBookRepository;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.List;

@Module
abstract class InMemoryRepositoryModule {

    @Binds
    @Singleton
    abstract BookRepository provideBookRepository(InMemoryBookRepository inMemoryBookRepository);

    @Binds
    @Singleton
    abstract AuthorRepository bindAuthorRepository(InMemoryAuthorRepository inMemoryAuthorRepository);

    @Provides
    static List<Book> provideTestBooks() {
        return List.of(
                new Book(0, "Book 1", "Description 1", "Author 1", null),
                new Book(1, "Book 2", "Description 2", "Author 2", null),
                new Book(2, "Book 3", "Description 3", "Author 3", null)
        );
    }

    @Provides
    static List<Author> provideTestAuthors() {
        return List.of(
                new Author(0, "James", "King", LocalDate.of(1990, 1, 1)),
                new Author(1, "Charles", "Brown", LocalDate.of(1991, 2, 2)),
                new Author(2, "Elizabeth", "Marley", LocalDate.of(1992, 3, 3))
        );
    }
}
