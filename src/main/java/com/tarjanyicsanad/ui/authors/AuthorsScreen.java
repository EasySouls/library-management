package com.tarjanyicsanad.ui.authors;

import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class AuthorsScreen extends JPanel {
    public AuthorsScreen(AuthorRepository repository) {
        setLayout(new BorderLayout());

        AuthorsTableModel tableModel = new AuthorsTableModel(repository);

        JTable authorsTable = new JTable();
        authorsTable.setModel(tableModel);
        authorsTable.setFillsViewportHeight(true);
        authorsTable.setRowSorter(new TableRowSorter<>(tableModel));

        add(new JScrollPane(authorsTable));

        AuthorSidePanel sidePanel = new AuthorSidePanel(book -> tableModel.removeAuthor(book.id()));
        add(sidePanel, BorderLayout.EAST);

        authorsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = authorsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = authorsTable.convertRowIndexToModel(selectedRow);
                    Optional<Author> selectedAuthorOpt = repository.getAuthor(modelRow);
                    if (selectedAuthorOpt.isEmpty()) {
                        return;
                    }
                    Author selectedAuthor = selectedAuthorOpt.get();
                    sidePanel.setData(selectedAuthor);
                }
            }
        });
    }
}
