package com.tarjanyicsanad.di;

import com.tarjanyicsanad.domain.repository.BookRepository;
import dagger.Component;

@Component(modules = RepositoryModule.class)
public interface RepositoryFactory {
    BookRepository bookRepository();
}
