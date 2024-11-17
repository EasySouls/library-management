package com.tarjanyicsanad.ui.authors;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class AuthorsScreen extends JPanel {
    public AuthorsScreen(AuthorsTableModel tableModel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTable authorsTable = new JTable();
        authorsTable.setModel(tableModel);
        authorsTable.setFillsViewportHeight(true);
        authorsTable.setRowSorter(new TableRowSorter<>(tableModel));

        add(new JScrollPane(authorsTable));
    }
}
