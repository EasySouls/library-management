package com.tarjanyicsanad.ui.members;

import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class MembersScreen extends JPanel {

    public MembersScreen(MemberRepository memberRepository) {
        setLayout(new BorderLayout());

        MembersTableModel tableModel = new MembersTableModel(memberRepository);

        JTable membersTable = new JTable();
        membersTable.setModel(tableModel);
        membersTable.setFillsViewportHeight(true);
        membersTable.setRowSorter(new TableRowSorter<>(tableModel));

        add(new JScrollPane(membersTable));

        MembersSidePanel sidePanel = new MembersSidePanel(member -> tableModel.removeMember(member.id()));
        add(sidePanel, BorderLayout.EAST);

        membersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = membersTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = membersTable.convertRowIndexToModel(selectedRow);
                    Optional<Member> selectedMemberOpt = memberRepository.getMember(modelRow);
                    if (selectedMemberOpt.isEmpty()) {
                        return;
                    }
                    Member selectedMember = selectedMemberOpt.get();
                    sidePanel.setData(selectedMember);
                }
            }
        });
    }
}