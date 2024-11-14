package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.BookRepository;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;

public class BooksTableModel extends AbstractTableModel {

    private final transient BookRepository bookRepository;

    public BooksTableModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public int getRowCount() {
        return bookRepository.findAll().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO: This only works if they are sorted by id
        Book student = bookRepository.getBook(rowIndex);
        return switch (columnIndex) {
            case 0 -> student.title();
            case 1 -> student.description();
            case 2 -> student.author();
            default -> student.publishingDate();
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Cím";
            case 1 -> "Leírás";
            case 2 -> "Szerző";
            case 3 -> "Kiadás éve";
            default -> "Egyéb";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 2 -> String.class;
            case 3 -> LocalDate.class;
            default -> Integer.class;
        };
    }
}
