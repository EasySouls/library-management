package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.domain.model.Book;
import org.apache.logging.log4j.util.TriConsumer;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BookSidePanel extends JPanel {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JTextField authorField;
    private final JTextField publishingDateField;

    private JTextField loanMemberEmailField;
    private JTextField loanBookNameField;
    JList<String> existingLoansList;

    private Book book;

    private final transient TriConsumer<String, String, String> onNewLoan;

    public BookSidePanel(Consumer<Book> onDelete, TriConsumer<String, String, String> onNewLoan) {
        this.onNewLoan = onNewLoan;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 0));

        JLabel titleLabel = new JLabel("Cím:");
        add(titleLabel);

        titleField = new JTextField();
        titleField.setMaximumSize(new Dimension(400, 20));
        add(titleField);

        JLabel descriptionLabel = new JLabel("Leírás:");
        add(descriptionLabel);

        descriptionArea = new JTextArea();
        descriptionArea.setMaximumSize(new Dimension(400, 20));
        add(descriptionArea);

        JLabel authorLabel = new JLabel("Szerző:");
        add(authorLabel);

        authorField = new JTextField();
        authorField.setMaximumSize(new Dimension(400, 20));
        add(authorField);

        JLabel publishingDateLabel = new JLabel("Kiadás éve:");
        add(publishingDateLabel);

        publishingDateField = new JTextField();
        publishingDateField.setMaximumSize(new Dimension(400, 20));
        add(publishingDateField);

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

        existingLoansList.setListData(book.loans().stream()
                .map(loan -> loan.member().name() + ", " + loan.loanedAt() + " - " + loan.returnDate())
                .toArray(String[]::new));
    }

    private JPanel loansPanel() {
        JPanel newLoanPanel;
        JPanel loansPanel = new JPanel();

        existingLoansList = new JList<>();
        existingLoansList.setMaximumSize(new Dimension(400, 100));
        existingLoansList.setListData(new String[0]);
        loansPanel.add(existingLoansList);

        newLoanPanel = new JPanel();
        loanBookNameField = new JTextField(6);
        loanMemberEmailField = new JTextField(6);
        newLoanPanel.add(new JLabel("Könyv címe"));
        newLoanPanel.add(loanBookNameField);
        newLoanPanel.add(new JLabel("Tag email címe"));
        newLoanPanel.add(loanMemberEmailField);
        JButton addNewLoanButton = new JButton("Új kölcsönzés");
        addNewLoanButton.addActionListener(_ -> addNewLoan());
        newLoanPanel.add(addNewLoanButton);
        loansPanel.add(newLoanPanel);

        return loansPanel;
    }

    private void addNewLoan() {
        String memberEmail = loanMemberEmailField.getText();
        String bookName = loanBookNameField.getText();
        String returnDate = JOptionPane.showInputDialog("Add meg a visszaadás dátumát (YYYY-MM-DD):");
        if (memberEmail.isEmpty() || bookName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A tag email címe és a könyv címe nem lehet üres!");
            return;
        }

        onNewLoan.accept(memberEmail, bookName, returnDate);
    }
}
