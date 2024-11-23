package com.tarjanyicsanad.di;

import com.tarjanyicsanad.data.authors.DatabaseAuthorRepository;
import com.tarjanyicsanad.data.books.DatabaseBookRepository;
import com.tarjanyicsanad.data.members.DatabaseMemberRepository;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import dagger.Module;
import dagger.Provides;
import org.hibernate.SessionFactory;

import javax.inject.Singleton;

@Module
public class DatabaseRepositoryModule {

    @Provides
    @Singleton
    BookRepository provideBookRepository(SessionFactory sessionFactory) {
        return new DatabaseBookRepository(sessionFactory);
    }

    @Provides
    @Singleton
    AuthorRepository provideAuthorRepository(SessionFactory sessionFactory) {
        return new DatabaseAuthorRepository(sessionFactory);
    }

    @Provides
    @Singleton
    MemberRepository provideMemberRepository(SessionFactory sessionFactory) {
        return new DatabaseMemberRepository(sessionFactory);
    }
}
