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
    /**
     * @return a new instance of {@link BookRepository}
     */
    BookRepository bookRepository();

    /**
     * @return a new instance of {@link AuthorRepository}
     */
    AuthorRepository authorRepository();

    /**
     * @return a new instance of {@link MemberRepository}
     */
    MemberRepository memberRepository();

    /**
     * @return a new instance of {@link LoanRepository}
     */
    LoanRepository loanRepository();

    /**
     * @return a new instance of {@link EntityManager}
     */
    EntityManager entityManager();
}
