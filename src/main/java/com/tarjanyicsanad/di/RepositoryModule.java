package com.tarjanyicsanad.di;

import com.tarjanyicsanad.data.books.InMemoryBookRepository;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.BookRepository;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import java.util.List;

@Module
abstract class RepositoryModule {

    @Binds
    abstract BookRepository bindBookRepository(InMemoryBookRepository inMemoryBookRepository);

    @Provides
    static List<Book> provideTestBooks() {
        return List.of(
                new Book(0, "Book 1", "Description 1", "Author 1", null),
                new Book(1, "Book 2", "Description 2", "Author 2", null),
                new Book(2, "Book 3", "Description 3", "Author 3", null)
        );
    }
}
