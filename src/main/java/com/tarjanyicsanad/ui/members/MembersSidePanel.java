package com.tarjanyicsanad.ui.members;

import com.tarjanyicsanad.domain.model.Member;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MembersSidePanel extends JPanel {
    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField joinedAtField;

    private Member member;

    public MembersSidePanel(Consumer<Member> onDelete) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 0));

        JLabel nameLabel = new JLabel("Név:");
        add(nameLabel);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(400, 20));
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel);

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(400, 20));
        add(emailField);

        JLabel joinedAtLabel = new JLabel("Csatlakozás dátuma:");
        add(joinedAtLabel);

        joinedAtField = new JTextField();
        joinedAtField.setMaximumSize(new Dimension(400, 20));
        add(joinedAtField);

        JButton deleteButton = new JButton("Törlés");
        deleteButton.addActionListener(e -> {
            onDelete.accept(member);
            setData(null);
        });
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        nameField.setEditable(false);
        emailField.setEditable(false);
        joinedAtField.setEditable(false);
    }

    public void setData(Member member) {
        this.member = member;
        if (member == null) {
            nameField.setText("");
            emailField.setText("");
            joinedAtField.setText("");
            return;
        }
        nameField.setText(member.name());
        emailField.setText(member.email());
        joinedAtField.setText(member.joinedAt().toString());
    }
}
