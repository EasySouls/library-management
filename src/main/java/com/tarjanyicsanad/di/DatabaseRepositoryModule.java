package com.tarjanyicsanad.di;

import com.tarjanyicsanad.data.authors.JpaAuthorRepository;
import com.tarjanyicsanad.data.books.JpaBookRepository;
import com.tarjanyicsanad.data.loans.JpaLoanRepository;
import com.tarjanyicsanad.data.members.JpaMemberRepository;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

/**
 * A Dagger module that binds the JPA implementations of the repositories to the interfaces.
 */
@Module
public abstract class DatabaseRepositoryModule {

    @Binds
    @Singleton
    abstract BookRepository bindBookRepository(JpaBookRepository jpaBookRepository);

    @Binds
    @Singleton
    abstract AuthorRepository bindAuthorRepository(JpaAuthorRepository jpaAuthorRepository);

    @Binds
    @Singleton
    abstract MemberRepository bindMemberRepository(JpaMemberRepository jpaMemberRepository);

    @Binds
    @Singleton
    abstract LoanRepository bindLoanRepository(JpaLoanRepository jpaLoanRepository);
}
