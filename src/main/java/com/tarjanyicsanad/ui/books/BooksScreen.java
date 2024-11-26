package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import com.tarjanyicsanad.ui.DateFieldKeyAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A panel that displays a table of {@link Book}s and allows the user to add and remove books.
 */
public class BooksScreen extends JPanel {

    private final JTextField titleField;
    private final JTextArea descriptionField;
    private final JTextField authorField;
    private final JFormattedTextField releaseYearField;

    private static final Logger logger = LogManager.getLogger(BooksScreen.class);

    private final BooksTableModel tableModel;

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    /**
     * Creates a new {@link BooksScreen}.
     *
     * @param bookRepository the repository to use for accessing books
     * @param authorRepository the repository to use for accessing authors
     * @param memberRepository the repository to use for accessing members
     * @param loanRepository the repository to use for accessing loans
     */
    @Inject
    public BooksScreen(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       MemberRepository memberRepository,
                       LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;

        setLayout(new BorderLayout());

        tableModel = new BooksTableModel(bookRepository, authorRepository);

        JComboBox<BooksTableModel.FilterOption> filterComboBox = new JComboBox<>(BooksTableModel.FilterOption.values());
        filterComboBox.addActionListener(e -> {
            BooksTableModel.FilterOption selectedFilter = (BooksTableModel.FilterOption) filterComboBox.getSelectedItem();
            assert selectedFilter != null;
            tableModel.setFilter(selectedFilter);
        });
        filterComboBox.setPreferredSize(new Dimension(200, 30));
        add(filterComboBox, BorderLayout.NORTH);

        JTable booksTable = new JTable();
        booksTable.setModel(tableModel);
        booksTable.setFillsViewportHeight(true);
        booksTable.setRowSorter(new TableRowSorter<>(tableModel));

        add(new JScrollPane(booksTable));

        BookSidePanel sidePanel = new BookSidePanel(
                book -> tableModel.removeBook(book.id()),
                this::onNewLoan,
                this::getLoansByBookTitle
        );
        add(sidePanel, BorderLayout.EAST);

        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = booksTable.convertRowIndexToModel(selectedRow);
                    String title = (String) tableModel.getValueAt(modelRow, 0);
                    Book selectedBook = bookRepository.findBookByTitle(title);
                    sidePanel.setData(selectedBook);
                }
            }
        });

        JPanel addBookPanel = new JPanel();
        addBookPanel.setPreferredSize(new Dimension(400, 200));
        addBookPanel.setBorder(BorderFactory.createTitledBorder("Könyv felvétele"));
        titleField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        authorField = new JTextField(20);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        releaseYearField = new JFormattedTextField(df);
        releaseYearField.setColumns(10);
        releaseYearField.addKeyListener(new DateFieldKeyAdapter());
        JButton addButton = getSaveNewBookButton(tableModel);

        addBookPanel.add(new JLabel("Cím:"));
        addBookPanel.add(titleField);
        addBookPanel.add(new JLabel("Leírás:"));
        addBookPanel.add(descriptionField);
        addBookPanel.add(new JLabel("Szerző neve:"));
        addBookPanel.add(authorField);
        addBookPanel.add(new JLabel("Kiadás éve:"));
        addBookPanel.add(releaseYearField);
        addBookPanel.add(addButton);
        this.add(addBookPanel, BorderLayout.SOUTH);
    }

    private JButton getSaveNewBookButton(BooksTableModel tableModel) {
        JButton addButton = new JButton("Felvesz");
        String logMessage = "Error while adding book";
        addButton.addActionListener(_ -> {
            try {
                tableModel.addBook(titleField.getText(), descriptionField.getText(), authorField.getText(), releaseYearField.getText());
            } catch (NoSuchElementException e) {
                logger.error(logMessage, e);
                JOptionPane.showMessageDialog(null, "Nem található ilyen szerző!");
            } catch (DateTimeException e) {
                logger.error(logMessage, e);
                JOptionPane.showMessageDialog(null, "Hibás dátum formátum!");
            } catch (Exception e) {
                logger.error(logMessage, e);
                JOptionPane.showMessageDialog(null, "Hiba történt a könyv hozzáadása közben!");
            }
        });
        return addButton;
    }

    private void onNewLoan(String bookName, String memberEmail, String returnDate) {
        logger.info("New loan for book {} by user {}", bookName, memberEmail);
        LocalDate returnDateParsed = LocalDate.parse(returnDate);
        try {
            Member member = memberRepository.findMemberByEmail(memberEmail);
            BookEntity book = bookRepository.findBookByTitle(bookName).toEntityWithId();
            bookRepository.addLoanToBook(book.getId(), member.email(), returnDateParsed);
            tableModel.fireTableDataChanged();
        } catch (MemberNotFoundException e) {
            logger.error("Member not found while adding loan", e);
            JOptionPane.showMessageDialog(null, "Nem található ilyen tag!");
        } catch (IllegalArgumentException e) {
            logger.error("Error while adding loan", e);
            JOptionPane.showMessageDialog(null, "A könyvet már kikölcsönözte ez a személy!");
        } catch (Exception e) {
            logger.error("Error while adding loan", e);
            JOptionPane.showMessageDialog(null, "Hiba történt a kölcsönzés hozzáadása közben!");
        }
    }

    private Set<Loan> getLoansByBookTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        return loanRepository.findLoansByBookId(book.id());
    }
}
