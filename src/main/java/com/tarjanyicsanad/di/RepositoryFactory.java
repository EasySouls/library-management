package com.tarjanyicsanad.di;

import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import dagger.Component;
import jakarta.persistence.EntityManager;

import javax.inject.Singleton;

/**
 * A Dagger component that provides repositories and an entity manager.
 */
@Component(modules = {
//        InMemoryRepositoryModule.class,
        DatabaseRepositoryModule.class,
        DatabaseModule.class
})
@Singleton
public interface RepositoryFactory {
    BookRepository bookRepository();
    AuthorRepository authorRepository();
    MemberRepository memberRepository();
    LoanRepository loanRepository();
    EntityManager entityManager();
}
