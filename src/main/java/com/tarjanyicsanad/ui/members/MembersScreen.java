package com.tarjanyicsanad.ui.members;

import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A panel that displays a table of {@link Member}s and allows the user to delete them.
 */
public class MembersScreen extends JPanel {

    /**
     * Creates a new {@link MembersScreen}.
     *
     * @param memberRepository the repository to use for accessing members
     */
    public MembersScreen(MemberRepository memberRepository) {
        setLayout(new BorderLayout());

        MembersTableModel tableModel = new MembersTableModel(memberRepository);

        JComboBox<MembersTableModel.FilterOption> filterComboBox = new JComboBox<>(MembersTableModel.FilterOption.values());
        filterComboBox.addActionListener(_ -> {
            MembersTableModel.FilterOption selectedFilter = (MembersTableModel.FilterOption) filterComboBox.getSelectedItem();
            assert selectedFilter != null;
            tableModel.setFilter(selectedFilter);
        });
        filterComboBox.setPreferredSize(new Dimension(200, 30));
        add(filterComboBox, BorderLayout.NORTH);

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
                    String email = (String) tableModel.getValueAt(modelRow, 1);
                    Member selectedMember = memberRepository.findMemberByEmail(email);
                    sidePanel.setData(selectedMember);
                }
            }
        });
    }
}
