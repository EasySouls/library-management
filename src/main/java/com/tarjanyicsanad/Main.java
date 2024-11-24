package com.tarjanyicsanad;

import com.tarjanyicsanad.data.DatabaseSeeder;
import com.tarjanyicsanad.di.DaggerRepositoryFactory;
import com.tarjanyicsanad.di.RepositoryFactory;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import com.tarjanyicsanad.ui.ApplicationFrame;
import jakarta.persistence.EntityManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = DaggerRepositoryFactory.create();
        BookRepository bookRepository = repositoryFactory.bookRepository();
        AuthorRepository authorRepository = repositoryFactory.authorRepository();
        MemberRepository memberRepository = repositoryFactory.memberRepository();
        EntityManager entityManager = repositoryFactory.entityManager();

        // Seed the database before starting the program
//        DatabaseSeeder.seed(entityManager);

        SwingUtilities.invokeLater(() -> {
            ApplicationFrame applicationFrame = new ApplicationFrame(
                    "Library Management",
                    1600,
                    900,
                    bookRepository,
                    authorRepository,
                    memberRepository
            );
            applicationFrame.show();
        });
    }
}