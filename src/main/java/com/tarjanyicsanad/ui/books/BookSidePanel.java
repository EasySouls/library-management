package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.ui.DateFieldKeyAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.TriConsumer;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A panel that displays the details of a {@link Book} and allows the user to delete it.
 */
public class BookSidePanel extends JPanel {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JTextField authorField;
    private final JTextField publishingDateField;

    private JTextField loanMemberEmailField;
    private JFormattedTextField returnDateField;
    JList<String> existingLoansList;

    private Book book;

    private final transient TriConsumer<String, String, String> onNewLoan;
    private final transient Function<String, Set<Loan>> getLoansByBookTitle;

    private static final Logger logger = LogManager.getLogger(BookSidePanel.class);


    public BookSidePanel(Consumer<Book> onDelete,
                         TriConsumer<String, String, String> onNewLoan,
                         Function<String, Set<Loan>> getLoansByBookTitle) {
        this.onNewLoan = onNewLoan;
        this.getLoansByBookTitle = getLoansByBookTitle;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 0));


        JLabel titleLabel = new JLabel("Cím:");
        add(titleLabel, BorderLayout.LINE_START);

        titleField = new JTextField();
        titleField.setMaximumSize(new Dimension(400, 20));
        add(titleField);
        add(Box.createVerticalStrut(10));

        JLabel descriptionLabel = new JLabel("Leírás:", SwingConstants.LEFT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(descriptionLabel, BorderLayout.LINE_START);

        descriptionArea = new JTextArea();
        descriptionArea.setMaximumSize(new Dimension(400, 20));
        add(descriptionArea);
        add(Box.createVerticalStrut(10));

        JLabel authorLabel = new JLabel("Szerző:", SwingConstants.LEFT);
        add(authorLabel, BorderLayout.LINE_START);

        authorField = new JTextField();
        authorField.setMaximumSize(new Dimension(400, 20));
        add(authorField);
        add(Box.createVerticalStrut(10));

        JLabel publishingDateLabel = new JLabel("Kiadás éve:", SwingConstants.LEFT);
        add(publishingDateLabel, BorderLayout.LINE_START);

        publishingDateField = new JTextField();
        publishingDateField.setMaximumSize(new Dimension(400, 20));
        add(publishingDateField);
        add(Box.createVerticalStrut(10));

        JButton deleteButton = new JButton("Törlés");
        deleteButton.addActionListener(e -> {
            onDelete.accept(book);
            setData(null);
        });
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        titleField.setEditable(false);
        descriptionArea.setEditable(false);
        authorField.setEditable(false);
        publishingDateField.setEditable(false);

        add(Box.createVerticalStrut(30));
        add(loansPanel());
    }

    public void setData(Book book) {
        this.book = book;
        if (book == null) {
            titleField.setText("");
            descriptionArea.setText("");
            authorField.setText("");
            publishingDateField.setText("");
            return;
        }
        titleField.setText(book.title());
        descriptionArea.setText(book.description());
        authorField.setText(book.author().fullName());
        publishingDateField.setText(book.publishingDate().toString());

        Set<Loan> loans = getLoansByBookTitle.apply(book.title());
        if (loans.isEmpty()) {
            existingLoansList.setListData(new String[]{"Nincs kölcsönzés"});
            return;
        }
        existingLoansList.setListData(loans.stream()
                .map(loan -> loan.member().name() + ", " + loan.loanedAt() + " - " + loan.returnDate())
                .toArray(String[]::new));
    }

    private JPanel loansPanel() {
        JPanel loansPanel = new JPanel();
        loansPanel.setLayout(new BoxLayout(loansPanel, BoxLayout.Y_AXIS));
        loansPanel.add(new JLabel("Kölcsönzések:"));
        existingLoansList = new JList<>();
        existingLoansList.setMaximumSize(new Dimension(400, 100));
        existingLoansList.setListData(new String[]{"Nincs kölcsönzés"});
        loansPanel.add(existingLoansList);
        loansPanel.add(Box.createVerticalStrut(30));


        JPanel newLoanPanel = new JPanel();
        newLoanPanel.setLayout(new BoxLayout(newLoanPanel, BoxLayout.Y_AXIS));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        returnDateField = new JFormattedTextField(df);
        returnDateField.setColumns(10);
        returnDateField.addKeyListener(new DateFieldKeyAdapter());
        loanMemberEmailField = new JTextField(6);
        newLoanPanel.add(new JLabel("Tag email címe"));
        newLoanPanel.add(loanMemberEmailField);
        newLoanPanel.add(new JLabel("Visszahozás dátuma"));
        newLoanPanel.add(returnDateField);
        JButton addNewLoanButton = new JButton("Új kölcsönzés");
        addNewLoanButton.addActionListener(_ -> addNewLoan());
        newLoanPanel.add(addNewLoanButton);
        loansPanel.add(newLoanPanel);

        return loansPanel;
    }

    private void addNewLoan() {
        String memberEmail = loanMemberEmailField.getText();
        String bookName = titleField.getText();
        String returnDate = returnDateField.getText();
        logger.info("New loan for book {} by user {}", bookName, memberEmail);
        if (memberEmail.isEmpty() || bookName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A tag email címe és a könyv címe nem lehet üres!");
            return;
        }

        onNewLoan.accept(bookName, memberEmail, returnDate);
    }
}
