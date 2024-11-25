package com.tarjanyicsanad.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A utility class for seeding the database with initial data.
 */
public class DatabaseSeeder {
    private DatabaseSeeder() {
    }

    private static final Logger logger = LogManager.getLogger(DatabaseSeeder.class);

    /**
     * Seeds the database with initial data.
     * @param entityManager the entity manager to use for seeding
     */
    public static void seed(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Execute native SQL to seed the database
            entityManager.createNativeQuery(new String(Files.readAllBytes(
                    Paths.get(Objects.requireNonNull(DatabaseSeeder.class.getClassLoader().getResource("seed.sql")).toURI())
            ))).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Database seeding failed", e);
            throw new RuntimeException("Database seeding failed", e);
        }
    }
}
