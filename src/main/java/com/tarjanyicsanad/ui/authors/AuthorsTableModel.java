package com.tarjanyicsanad.ui.authors;

import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.repository.AuthorRepository;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.Set;

public class AuthorsTableModel extends AbstractTableModel {

    private final transient AuthorRepository authorRepository;

    public AuthorsTableModel(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void addAuthor(String firstName, String lastName, String dateOfBirthString) {
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString);
        authorRepository.addAuthor(new Author(0, firstName, lastName, dateOfBirth, Set.of()));
        fireTableDataChanged();
    }

    public void removeAuthor(int id) {
        authorRepository.removeAuthor(id);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return authorRepository.findAllAuthors().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> authorRepository.findAllAuthors().get(rowIndex).firstName();
            case 1 -> authorRepository.findAllAuthors().get(rowIndex).lastName();
            case 2 -> authorRepository.findAllAuthors().get(rowIndex).dateOfBirth();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Keresztnév";
            case 1 -> "Vezetéknév";
            case 2 -> "Születési dátum";
            default -> "Egyéb";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1 -> String.class;
            case 2 -> LocalDate.class;
            default -> Integer.class;
        };
    }
}
