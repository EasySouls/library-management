package com.tarjanyicsanad.ui.books;


import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.BookRepository;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class BooksScreen extends JPanel {

    @Inject
    public BooksScreen(BookRepository bookRepository) {
        setLayout(new BorderLayout());

        BooksTableModel tableModel = new BooksTableModel(bookRepository);

        JTable booksTable = new JTable();
        booksTable.setModel(tableModel);
        booksTable.setFillsViewportHeight(true);
        booksTable.setRowSorter(new TableRowSorter<>(tableModel));

        add(new JScrollPane(booksTable));

        BookSidePanel sidePanel = new BookSidePanel(book -> tableModel.removeBook(book.id()));
        add(sidePanel, BorderLayout.EAST);

        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = booksTable.convertRowIndexToModel(selectedRow);
                    Optional<Book> selectedBookOpt = bookRepository.getBook(modelRow);
                    if (selectedBookOpt.isEmpty()) {
                        return;
                    }
                    Book selectedBook = selectedBookOpt.get();
                    sidePanel.setData(selectedBook);
                }
            }
        });
    }
}
