package com.tarjanyicsanad.ui.books;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class BooksScreen extends JPanel {
    public BooksScreen(BooksTableModel tableModel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTable booksTable = new JTable();
        booksTable.setModel(tableModel);
        booksTable.setFillsViewportHeight(true);
        booksTable.setRowSorter(new TableRowSorter<>(tableModel));

        add(new JScrollPane(booksTable));
    }
}
