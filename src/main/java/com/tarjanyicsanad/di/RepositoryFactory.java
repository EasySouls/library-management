package com.tarjanyicsanad.di;

import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {
        InMemoryRepositoryModule.class,
//        DatabaseRepositoryModule.class,
//        DatabaseModule.class
})
@Singleton
public interface RepositoryFactory {
    BookRepository bookRepository();
    AuthorRepository authorRepository();
}
