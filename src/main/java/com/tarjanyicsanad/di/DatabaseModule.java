package com.tarjanyicsanad.di;

import dagger.Module;
import dagger.Provides;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.inject.Singleton;

/**
 * A Dagger module that provides database-related dependencies.
 */
@Module
public class DatabaseModule {

    /**
     * Provides a singleton instance of SessionFactory.
     *
     * @return a configured SessionFactory instance.
     */
    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    /**
     * Provides a singleton instance of EntityManagerFactory.
     *
     * @return a configured EntityManagerFactory instance.
     */
    @Provides
    @Singleton
    public EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("libraryManagement");
    }

    /**
     * Provides a singleton instance of EntityManager.
     *
     * @param entityManagerFactory the EntityManagerFactory to create the EntityManager from.
     * @return a configured EntityManager instance.
     */
    @Provides
    @Singleton
    public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
}
