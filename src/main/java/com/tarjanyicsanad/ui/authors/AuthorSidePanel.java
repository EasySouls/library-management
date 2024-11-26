package com.tarjanyicsanad.ui.authors;

import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * A panel that displays the details of an {@link Author} and allows the user to delete it.
 */
public class AuthorSidePanel extends JPanel {
    private final JTextField nameField;
    private final JTextField dateOfBirthField;
    private final JList<String> booksList;

    private Author author;

    /**
     * Creates a new {@link AuthorSidePanel}.
     *
     * @param onDelete a callback that will be called when the user wants to delete the author
     */
    public AuthorSidePanel(Consumer<Author> onDelete) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 0));

        JLabel nameLabel = new JLabel("Név:");
        add(nameLabel);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(400, 20));
        add(nameField);
        add(Box.createVerticalStrut(10));

        JLabel dateOfBirthLabel = new JLabel("Születési év:");
        add(dateOfBirthLabel);

        dateOfBirthField = new JTextField();
        dateOfBirthField.setMaximumSize(new Dimension(400, 20));
        add(dateOfBirthField);
        add(Box.createVerticalStrut(10));

        JButton deleteButton = new JButton("Törlés");
        deleteButton.addActionListener(e -> {
            onDelete.accept(author);
            setData(null);
        });
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);
        add(Box.createVerticalStrut(30));

        nameField.setEditable(false);
        dateOfBirthField.setEditable(false);

        add(new JLabel("Kiadott könyvek:"));

        booksList = new JList<>();
        booksList.setMaximumSize(new Dimension(400, 100));
        booksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        booksList.setListData(new String[0]);
        add(new JScrollPane(booksList));
    }

    /**
     * Sets the data to be displayed in the panel.
     *
     * @param author the author to display, or {@code null} to clear the panel
     */
    public void setData(Author author) {
        this.author = author;
        if (author == null) {
            nameField.setText("");
            dateOfBirthField.setText("");
            return;
        }
        nameField.setText(author.firstName() + " " + author.lastName());
        dateOfBirthField.setText(author.dateOfBirth().toString());
        booksList.setListData(author.books().stream().map(Book::title).toArray(String[]::new));
    }
}
