package com.tarjanyicsanad.ui.books;


import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class BooksScreen extends JPanel {

    private final JTextField titleField;
    private final JTextArea descriptionField;
    private final JTextField authorField;
    private final JFormattedTextField releaseYearField;

    private static final Logger logger = LogManager.getLogger(BooksScreen.class);

    @Inject
    public BooksScreen(BookRepository bookRepository, AuthorRepository authorRepository) {
        setLayout(new BorderLayout());

        BooksTableModel tableModel = new BooksTableModel(bookRepository, authorRepository);

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

        JPanel addBookPanel = new JPanel();
        addBookPanel.setPreferredSize(new Dimension(400, 200));
        addBookPanel.setBorder(BorderFactory.createTitledBorder("Könyv felvétele"));
        titleField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        authorField = new JTextField(20);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        releaseYearField = new JFormattedTextField(df);
        releaseYearField.setColumns(10);
        releaseYearField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_MINUS)))
                {
                    JOptionPane.showMessageDialog(null, "Kérlek YYYY-MM-DD formátumban add meg az évszámot!");
                    e.consume();
                }
            }
        });
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
}
