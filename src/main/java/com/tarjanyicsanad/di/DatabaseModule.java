package com.tarjanyicsanad.di;

import dagger.Module;
import dagger.Provides;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.inject.Singleton;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
