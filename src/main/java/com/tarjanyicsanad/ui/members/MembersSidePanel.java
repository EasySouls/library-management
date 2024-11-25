package com.tarjanyicsanad.ui.members;

import com.tarjanyicsanad.domain.model.Member;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * A panel that displays the details of a {@link Member} and allows the user to delete it.
 */
public class MembersSidePanel extends JPanel {
    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField joinedAtField;

    private JList<String> loansList;

    private Member member;

    public MembersSidePanel(Consumer<Member> onDelete) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 0));

        JLabel nameLabel = new JLabel("Név:");
        add(nameLabel);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(400, 20));
        add(nameField);
        add(Box.createVerticalStrut(10));

        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel);

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(400, 20));
        add(emailField);
        add(Box.createVerticalStrut(10));

        JLabel joinedAtLabel = new JLabel("Csatlakozás dátuma:");
        add(joinedAtLabel);

        joinedAtField = new JTextField();
        joinedAtField.setMaximumSize(new Dimension(400, 20));
        add(joinedAtField);
        add(Box.createVerticalStrut(10));

        JButton deleteButton = new JButton("Törlés");
        deleteButton.addActionListener(e -> {
            onDelete.accept(member);
            setData(null);
        });
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);
        add(Box.createVerticalStrut(30));

        nameField.setEditable(false);
        emailField.setEditable(false);
        joinedAtField.setEditable(false);

        add(loansList());
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

        if (member.loans().isEmpty()) {
            loansList.setListData(new String[]{"Nincsenek kölcsönzések"});
        } else {
            loansList.setListData(member.loans().stream()
                    .map(loan -> loan.book().title() + (loan.isLoaned() ? " - Aktív" : ""))
                    .toArray(String[]::new));
        }
    }

    private JScrollPane loansList() {
        JPanel loansPanel = new JPanel();
        loansPanel.setLayout(new BorderLayout());
        JLabel loansLabel = new JLabel("Kölcsönzések:");
        loansPanel.add(loansLabel, BorderLayout.NORTH);
        loansPanel.add(Box.createVerticalStrut(10));
        loansList = new JList<>();

        loansList.setMaximumSize(new Dimension(400, 400));
        loansList.setPreferredSize(new Dimension(400, 100));
        loansList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loansList.setListData(new String[]{"Nincsenek kölcsönzések"});
        loansPanel.add(loansList, BorderLayout.CENTER);
        return new JScrollPane(loansPanel);
    }

    public void setEditable(boolean editable) {
        nameField.setEditable(editable);
        emailField.setEditable(editable);
        joinedAtField.setEditable(editable);
    }
}
