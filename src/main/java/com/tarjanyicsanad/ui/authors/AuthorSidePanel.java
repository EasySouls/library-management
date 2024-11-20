package com.tarjanyicsanad.ui.authors;

import com.tarjanyicsanad.domain.model.Author;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class AuthorSidePanel extends JPanel {
    private final JTextField nameField;
    private final JTextField dateOfBirthField;

    private Author author;

    public AuthorSidePanel(Consumer<Author> onDelete) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 0));

        JLabel nameLabel = new JLabel("Név:");
        add(nameLabel);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(400, 20));
        add(nameField);

        JLabel dateOfBirthLabel = new JLabel("Születési év:");
        add(dateOfBirthLabel);

        dateOfBirthField = new JTextField();
        dateOfBirthField.setMaximumSize(new Dimension(400, 20));
        add(dateOfBirthField);

        JButton deleteButton = new JButton("Törlés");
        deleteButton.addActionListener(e -> onDelete.accept(author));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        nameField.setEditable(false);
        dateOfBirthField.setEditable(false);
    }

    public void setData(Author author) {
        this.author = author;
        nameField.setText(author.firstName() + " " + author.lastName());
        if (author.dateOfBirth() == null) {
            dateOfBirthField.setText("");
            return;
        }
        dateOfBirthField.setText(author.dateOfBirth().toString());
    }
}
