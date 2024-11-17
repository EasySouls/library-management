package com.tarjanyicsanad.di;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import dagger.Module;
import dagger.Provides;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.inject.Singleton;

import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        boolean showSql = false;
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(BookEntity.class)
                // use H2 in-memory database
                .setProperty(JAKARTA_JDBC_URL, "jdbc:h2:mem:db1")
                .setProperty(JAKARTA_JDBC_USER, "sa")
                .setProperty(JAKARTA_JDBC_PASSWORD, "")
                // display SQL in console
                .setProperty(SHOW_SQL, showSql)
                .setProperty(FORMAT_SQL, showSql)
                .setProperty(HIGHLIGHT_SQL, showSql)
                .buildSessionFactory();

        // export the inferred database schema
        sessionFactory.getSchemaManager().exportMappedObjects(true);

        return sessionFactory;
    }
}
