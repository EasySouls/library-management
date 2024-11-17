package com.tarjanyicsanad.ui.authors;

import com.tarjanyicsanad.domain.repository.AuthorRepository;

import javax.swing.table.AbstractTableModel;

public class AuthorsTableModel extends AbstractTableModel {
    private final transient AuthorRepository authorRepository;

    public AuthorsTableModel(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public int getRowCount() {
        return authorRepository.findAll().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> authorRepository.findAll().get(rowIndex).firstName();
            case 1 -> authorRepository.findAll().get(rowIndex).lastName();
            case 2 -> authorRepository.findAll().get(rowIndex).dateOfBirth();
            default -> null;
        };
    }
}
