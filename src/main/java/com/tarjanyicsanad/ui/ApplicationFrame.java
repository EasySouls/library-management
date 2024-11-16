package com.tarjanyicsanad.ui;

import com.tarjanyicsanad.di.DaggerRepositoryFactory;
import com.tarjanyicsanad.di.RepositoryFactory;
import com.tarjanyicsanad.domain.repository.BookRepository;
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

    public ApplicationFrame(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        RepositoryFactory repositoryFactory = DaggerRepositoryFactory.create();
        bookRepository = repositoryFactory.bookRepository();
        booksTableModel = new BooksTableModel(bookRepository);

        screensLayout = new CardLayout();
        screensLayout.addLayoutComponent(new BooksScreen(booksTableModel), Screens.BOOKS);
        screens = new JPanel(screensLayout);
        screens.add(new BooksScreen(booksTableModel), Screens.BOOKS);

        frame.setJMenuBar(new MenuBar(layout -> screensLayout.show(screens, layout)));
        frame.add(screens);


//        frame.add(new BooksScreen(booksTableModel), BorderLayout.CENTER);

        /// Handle reading saved books from file
    }

    public void show() {
        frame.setVisible(true);
    }
}
