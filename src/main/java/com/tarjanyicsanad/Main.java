package com.tarjanyicsanad;

import com.tarjanyicsanad.di.DaggerRepositoryFactory;
import com.tarjanyicsanad.di.RepositoryFactory;
import com.tarjanyicsanad.ui.ApplicationFrame;
import jakarta.persistence.EntityManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = DaggerRepositoryFactory.create();
        EntityManager entityManager = repositoryFactory.entityManager();

        // Seed the database before starting the program
//        DatabaseSeeder.seed(entityManager);

        SwingUtilities.invokeLater(() -> {
            ApplicationFrame applicationFrame = new ApplicationFrame(
                    "Library Management",
                    1600,
                    900,
                    repositoryFactory.bookRepository(),
                    repositoryFactory.authorRepository(),
                    repositoryFactory.memberRepository(),
                    repositoryFactory.loanRepository()

            );
            applicationFrame.show();
        });
    }
}