package com.tarjanyicsanad.ui;

import com.tarjanyicsanad.di.DaggerRepositoryFactory;
import com.tarjanyicsanad.di.RepositoryFactory;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.ui.authors.AuthorsScreen;
import com.tarjanyicsanad.ui.authors.AuthorsTableModel;
import com.tarjanyicsanad.ui.books.BooksScreen;
import com.tarjanyicsanad.ui.books.BooksTableModel;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame {
    private JFrame frame;

    private JPanel screens;
    private CardLayout screensLayout;

    private BookRepository bookRepository;
    private BooksTableModel booksTableModel;
    private AuthorRepository authorRepository;
    private AuthorsTableModel authorsTableModel;

    public ApplicationFrame(String title, int width, int height) {
        /// Handle dependency injection
        RepositoryFactory repositoryFactory = DaggerRepositoryFactory.create();
        bookRepository = repositoryFactory.bookRepository();
        authorRepository = repositoryFactory.authorRepository();

        /// Handle reading saved data if it is enabled

        initComponents(title, width, height);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initComponents(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        booksTableModel = new BooksTableModel(bookRepository);
        authorsTableModel = new AuthorsTableModel(authorRepository);

        screensLayout = new CardLayout();
        screensLayout.addLayoutComponent(new BooksScreen(booksTableModel), Screens.BOOKS);
        screensLayout.addLayoutComponent(new AuthorsScreen(authorsTableModel), Screens.AUTHORS);

        screens = new JPanel(screensLayout);
        screens.add(new BooksScreen(booksTableModel), Screens.BOOKS);
        screens.add(new AuthorsScreen(authorsTableModel), Screens.AUTHORS);

        frame.setJMenuBar(new MenuBar(layout -> screensLayout.show(screens, layout)));
        frame.add(screens);
    }
}
