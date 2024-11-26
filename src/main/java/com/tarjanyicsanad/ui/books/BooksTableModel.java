package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A table model for displaying {@link Book}s in a {@link javax.swing.JTable}.
 */
public class BooksTableModel extends AbstractTableModel {

    private final transient BookRepository bookRepository;
    private final transient AuthorRepository authorRepository;

    private List<Book> filteredBooks;
    private FilterOption filter;

    /**
     * Creates a new {@link BooksTableModel}.
     *
     * @param bookRepository the repository to use for accessing books
     * @param authorRepository the repository to use for accessing authors
     */
    public BooksTableModel(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

        this.filter = FilterOption.ALL;
        applyFilter();
    }

    /**
     * Adds a new book to the table.
     *
     * @param title the title of the book
     * @param description the description of the book
     * @param authorName the name of the author of the book
     * @param publishingDateString the publishing date of the book
     */
    public void addBook(String title, String description, String authorName, String publishingDateString) {
        LocalDate publishingDate = LocalDate.parse(publishingDateString);
        String lastName = authorName.split(" ")[0];
        String firstName = authorName.split(" ")[1];

        Author author = Author.fromEntity(authorRepository.findAuthorByName(firstName, lastName)
                .orElseThrow());
        // We set the author to null because the author needs to be a persisted entity,
        // so we add it later in the repository
        bookRepository.addBook(new Book(0, title, description, author, Set.of(), publishingDate));
        applyFilter();
    }

    /**
     * Removes a book from the table.
     *
     * @param id the ID of the book to remove
     */
    public void removeBook(int id) {
        bookRepository.removeBook(id);
        applyFilter();
    }

    @Override
    public int getRowCount() {
        return filteredBooks.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = filteredBooks.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> book.title();
            case 1 -> book.description();
            case 2 -> book.author().fullName();
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

    public void refreshData() {
        applyFilter();
    }

    /**
     * Sets the filter to apply to the table.
     *
     * @param filter the filter to apply
     */
    public void setFilter(FilterOption filter) {
        this.filter = filter;
        applyFilter();
    }

    private void applyFilter() {
        List<Book> books = bookRepository.findAllBooks();
        filteredBooks = new ArrayList<>();
        for (Book book : books) {
            switch (filter) {
                case FilterOption.ALL -> filteredBooks.add(book);
                case LOANED -> {
                    for (Loan loan : book.loans()) {
                        if (loan.isLoaned()) {
                            filteredBooks.add(book);
                            break;
                        }
                    }
                }
                case NOT_LOANED -> {
                    boolean isLoaned = false;
                    for (Loan loan : book.loans()) {
                        if (loan.isLoaned()) {
                            isLoaned = true;
                            break;
                        }
                    }
                    if (!isLoaned) {
                        filteredBooks.add(book);
                    }
                }
            }
            fireTableDataChanged();
        }
    }

    /**
     * An enum representing the filter options for the table model.
     */
    public enum FilterOption {
        /**
         * Show all books.
         */
        ALL,

        /**
         * Show only loaned books.
         */
        LOANED,

        /**
         * Show only not loaned books.
         */
        NOT_LOANED
    }
}

