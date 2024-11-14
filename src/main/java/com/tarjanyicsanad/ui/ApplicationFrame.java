package com.tarjanyicsanad.ui;

import com.tarjanyicsanad.di.DaggerRepositoryFactory;
import com.tarjanyicsanad.di.RepositoryFactory;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.ui.books.BooksTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class ApplicationFrame {
    private final JFrame frame;

    private final MenuBar menuBar;

    private final BookRepository bookRepository;
    private final BooksTableModel booksTableModel;

    public ApplicationFrame(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);

        RepositoryFactory repositoryFactory = DaggerRepositoryFactory.create();
        bookRepository = repositoryFactory.bookRepository();
        booksTableModel = new BooksTableModel(bookRepository);

        JTable booksTable = new JTable();
        booksTable.setModel(booksTableModel);
        booksTable.setFillsViewportHeight(true);
        booksTable.setRowSorter(new TableRowSorter<>(booksTableModel));
        System.out.printf("Starting books row count: %d", booksTableModel.getRowCount());
        frame.add(new JScrollPane(booksTable));
    }

    public void show() {
        frame.setVisible(true);
    }
}
