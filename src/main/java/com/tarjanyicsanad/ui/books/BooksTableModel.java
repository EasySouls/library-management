package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class BooksTableModel extends AbstractTableModel {

    private final transient BookRepository bookRepository;
    private final transient AuthorRepository authorRepository;

    public BooksTableModel(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void addBook(String title, String description, String authorName, String publishingDateString) {
        LocalDate publishingDate = LocalDate.parse(publishingDateString);
        String firstName = authorName.split(" ")[0];
        String lastName = authorName.split(" ")[1];


        Author author = Author.fromEntity(authorRepository.findAuthorByName(firstName, lastName)
                .orElseThrow());
        // We set the author to null because the author needs to be a persisted entity,
        // so we add it later in the repository
        bookRepository.addBook(new Book(0, title, description, author, Set.of(), publishingDate));
        fireTableDataChanged();
    }

    public void removeBook(int id) {
        bookRepository.removeBook(id);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return bookRepository.findAllBooks().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Optional<Book> bookOpt = Optional.ofNullable(bookRepository.findAllBooks().get(rowIndex));
        if (bookOpt.isEmpty()) {
            return null;
        }
        Book book = bookOpt.get();
        return switch (columnIndex) {
            case 0 -> book.title();
            case 1 -> book.description();
            case 2 -> book.author();
            case 3 -> book.publishingDate();
            default -> "";
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
