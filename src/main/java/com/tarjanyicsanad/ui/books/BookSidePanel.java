package com.tarjanyicsanad.ui.books;

import com.tarjanyicsanad.domain.model.Book;

import javax.swing.*;
import java.awt.*;

public class BookSidePanel extends JPanel {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JTextField authorField;
    private final JTextField publishingDateField;

    public BookSidePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 0));

        JLabel titleLabel = new JLabel("Cím:");
        add(titleLabel);
        titleField = new JTextField();
        add(titleField);
        JLabel descriptionLabel = new JLabel("Leírás:");
        add(descriptionLabel);
        descriptionArea = new JTextArea();
        add(descriptionArea);
        JLabel authorLabel = new JLabel("Szerző:");
        add(authorLabel);
        authorField = new JTextField();
        add(authorField);
        JLabel publishingDateLabel = new JLabel("Kiadás éve:");
        add(publishingDateLabel);
        publishingDateField = new JTextField();
        add(publishingDateField);

        titleField.setEditable(false);
        descriptionArea.setEditable(false);
        authorField.setEditable(false);
        publishingDateField.setEditable(false);
    }

    public void setData(Book book) {
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
