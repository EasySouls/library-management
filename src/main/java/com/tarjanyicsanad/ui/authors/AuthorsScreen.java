package com.tarjanyicsanad.ui.authors;

import com.tarjanyicsanad.domain.exceptions.AuthorNotFoundException;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

/**
 * Screen displaying a table of {@link Author}s and a side panel for adding and removing authors.
 */
public class AuthorsScreen extends JPanel {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JFormattedTextField dateOfBirthField;

    private static final Logger logger = LogManager.getLogger(AuthorsScreen.class);

    private final AuthorsTableModel tableModel;

    /**
     * Creates a new {@link AuthorsScreen}.
     *
     * @param repository the repository to use for accessing authors
     */
    public AuthorsScreen(AuthorRepository repository) {
        setLayout(new BorderLayout());

        tableModel = new AuthorsTableModel(repository);

        JTable authorsTable = new JTable();
        authorsTable.setModel(tableModel);
        authorsTable.setFillsViewportHeight(true);
        authorsTable.setRowSorter(new TableRowSorter<>(tableModel));

        add(new JScrollPane(authorsTable));

        AuthorSidePanel sidePanel = new AuthorSidePanel(this::removeAuthor);
        add(sidePanel, BorderLayout.EAST);

        authorsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = authorsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = authorsTable.convertRowIndexToModel(selectedRow);
                    String firstName = (String) tableModel.getValueAt(modelRow, 0);
                    String lastName = (String) tableModel.getValueAt(modelRow, 1);
                    Author selectedAuthor = Author.fromEntity(repository.findAuthorByName(firstName, lastName)
                            .orElseThrow(() -> new IllegalArgumentException("Author not found")));
                    sidePanel.setData(selectedAuthor);
                }
            }
        });

        JPanel addBookPanel = new JPanel();
        addBookPanel.setPreferredSize(new Dimension(400, 200));
        addBookPanel.setBorder(BorderFactory.createTitledBorder("Szerző felvétele"));
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateOfBirthField = new JFormattedTextField(df);
        dateOfBirthField.setColumns(10);
        dateOfBirthField.addKeyListener(new KeyAdapter() {
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
        JButton addButton = getSaveNewAuthorButton(tableModel);

        addBookPanel.add(new JLabel("Keresztnév:"));
        addBookPanel.add(firstNameField);
        addBookPanel.add(new JLabel("Vezetéknév:"));
        addBookPanel.add(lastNameField);
        addBookPanel.add(new JLabel("Születés éve:"));
        addBookPanel.add(dateOfBirthField);
        addBookPanel.add(addButton);
        this.add(addBookPanel, BorderLayout.SOUTH);
    }

    private JButton getSaveNewAuthorButton(AuthorsTableModel tableModel) {
        JButton addButton = new JButton("Felvesz");
        String logMessage = "Error while adding author";
        addButton.addActionListener(_ -> {
            try {
                tableModel.addAuthor(firstNameField.getText(), lastNameField.getText(), dateOfBirthField.getText());
            } catch (DateTimeException e) {
                logger.error(logMessage, e);
                JOptionPane.showMessageDialog(null, "Hibás dátum formátum!");
            } catch (Exception e) {
                logger.error(logMessage, e);
                JOptionPane.showMessageDialog(null, "Hiba történt a szerző hozzáadása közben!");
            }
        });
        return addButton;
    }

    private void removeAuthor(Author author) {
        try {
            if (!author.books().isEmpty()) {
                JOptionPane.showMessageDialog(null, "A szerzőnek vannak könyvei, ezért nem törölhető!");
                return;
            }
            tableModel.removeAuthor(author.id());
        } catch (AuthorNotFoundException e) {
            logger.error("Error while removing author", e);
            JOptionPane.showMessageDialog(null, "Hiba történt a szerző törlése közben!");
        }
    }
}
