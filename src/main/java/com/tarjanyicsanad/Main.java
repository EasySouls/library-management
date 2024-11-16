package com.tarjanyicsanad;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.ui.ApplicationFrame;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;

import static org.hibernate.cfg.JdbcSettings.*;

public class Main {

    public static void main(String[] args) {
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

        sessionFactory.inTransaction(session -> {
            BookEntity book = new BookEntity(1, "The Lord of the Rings", "A fantasy novel", "J. R. R. Tolkien");
            session.persist(book);
        });

        SwingUtilities.invokeLater(() -> {
            ApplicationFrame applicationFrame = new ApplicationFrame("Library Management", 1600, 900);
            applicationFrame.show();
        });

        sessionFactory.close();
    }
}