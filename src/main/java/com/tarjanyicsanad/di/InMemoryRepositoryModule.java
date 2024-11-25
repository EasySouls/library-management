package com.tarjanyicsanad.di;

import com.tarjanyicsanad.data.authors.InMemoryAuthorRepository;
import com.tarjanyicsanad.data.books.InMemoryBookRepository;
import com.tarjanyicsanad.data.members.InMemoryMemberRepository;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * A Dagger module that binds the in memory implementations of the repositories to the interfaces,
 * and provides test data for the repositories.
 */
@Module
abstract class InMemoryRepositoryModule {

    @Binds
    @Singleton
    abstract BookRepository bindBookRepository(InMemoryBookRepository inMemoryBookRepository);

    @Binds
    @Singleton
    abstract AuthorRepository bindAuthorRepository(InMemoryAuthorRepository inMemoryAuthorRepository);

    @Binds
    @Singleton
    abstract MemberRepository bindMemberRepository(InMemoryMemberRepository inMemoryMemberRepository);

    @Provides
    static List<Book> provideTestBooks() {
        Author author = new Author(0, "John", "Gray", LocalDate.of(1990, 1, 1), Set.of());
        return List.of(
                new Book(0, "Book 1", "Description 1", author, Set.of(), LocalDate.of(2021, 1, 1)),
                new Book(1, "Book 2", "Description 2", author, Set.of(), LocalDate.of(2021, 2, 2)),
                new Book(2, "Book 3", "Description 3", author, Set.of(), LocalDate.of(2021, 3, 3))
        );
    }

    @Provides
    static List<Author> provideTestAuthors() {
        return List.of(
                new Author(0, "James", "King", LocalDate.of(1990, 1, 1), Set.of()),
                new Author(1, "Charles", "Brown", LocalDate.of(1991, 2, 2), Set.of()),
                new Author(2, "Elizabeth", "Marley", LocalDate.of(1992, 3, 3), Set.of())
        );
    }

    @Provides
    static List<Member> provideTestMembers() {
        return List.of(
                new Member(0, "John", "Doe", LocalDate.of(1990, 1, 1), Set.of()),
                new Member(1, "Jane", "Doe", LocalDate.of(1991, 2, 2), Set.of()),
                new Member(2, "Alice", "Smith", LocalDate.of(1992, 3, 3), Set.of())
        );
    }
}
