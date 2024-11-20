package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.domain.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class BookSidePanel extends JPanel {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JTextField authorField;
    private final JTextField publishingDateField;

    private Book book;

    public BookSidePanel(Consumer<Book> onDelete) {
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
        deleteButton.addActionListener(e -> onDelete.accept(book));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        titleField.setEditable(false);
        descriptionArea.setEditable(false);
        authorField.setEditable(false);
        publishingDateField.setEditable(false);
    }

    public void setData(Book book) {
        this.book = book;
        titleField.setText(book.title());
        descriptionArea.setText(book.description());
        authorField.setText(book.author());
        if (book.publishingDate() == null) {
            publishingDateField.setText("");
            return;
        }
        publishingDateField.setText(book.publishingDate().toString());
    }
}
