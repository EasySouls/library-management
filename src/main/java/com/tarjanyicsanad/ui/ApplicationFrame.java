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
    private final JFrame frame;

    private final JPanel screens;
    private final CardLayout screensLayout;

    private final BookRepository bookRepository;
    private final BooksTableModel booksTableModel;
    private final AuthorRepository authorRepository;
    private final AuthorsTableModel authorsTableModel;

    public ApplicationFrame(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        RepositoryFactory repositoryFactory = DaggerRepositoryFactory.create();

        bookRepository = repositoryFactory.bookRepository();
        booksTableModel = new BooksTableModel(bookRepository);

        authorRepository = repositoryFactory.authorRepository();
        authorsTableModel = new AuthorsTableModel(authorRepository);

        screensLayout = new CardLayout();
        screensLayout.addLayoutComponent(new BooksScreen(booksTableModel), Screens.BOOKS);
        screensLayout.addLayoutComponent(new AuthorsScreen(authorsTableModel), Screens.AUTHORS);

        screens = new JPanel(screensLayout);
        screens.add(new BooksScreen(booksTableModel), Screens.BOOKS);
        screens.add(new AuthorsScreen(authorsTableModel), Screens.AUTHORS);

        frame.setJMenuBar(new MenuBar(layout -> screensLayout.show(screens, layout)));
        frame.add(screens);

        /// Handle reading saved books from file
    }

    public void show() {
        frame.setVisible(true);
    }
}
