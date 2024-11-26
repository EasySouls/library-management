package com.tarjanyicsanad.ui.members;

import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A table model for displaying {@link Member}s in a {@link javax.swing.JTable}.
 */
public class MembersTableModel extends AbstractTableModel {

    private final transient MemberRepository memberRepository;

    private final List<Member> filteredMembers;
    private FilterOption filter;

    public MembersTableModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.filteredMembers = memberRepository.findAllMembers();
        this.filter = FilterOption.ALL;
        applyFilter();
    }

    public void removeMember(int id) {
        memberRepository.removeMember(id);
        applyFilter();
    }

    @Override
    public int getRowCount() {
        return filteredMembers.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Optional<Member> memberOpt = Optional.ofNullable(memberRepository.findAllMembers().get(rowIndex));
        if (memberOpt.isEmpty()) {
            return null;
        }
        Member member = memberOpt.get();
        return switch (columnIndex) {
            case 0 -> member.name();
            case 1 -> member.email();
            case 2 -> member.joinedAt();
            default -> "";
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Név";
            case 1 -> "Email";
            case 2 -> "Csatlakozás dátuma";
            default -> "Egyéb";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1 -> String.class;
            case 2 -> LocalDate.class;
            default -> Object.class;
        };
    }

    public void setFilter(FilterOption filter) {
        this.filter = filter;
        applyFilter();
    }

    private void applyFilter() {
        List<Member> members = memberRepository.findAllMembers();
        filteredMembers.clear();
        List<Member> membersWithActiveLoans = memberRepository.findMembersWithActiveLoans();
        for (Member member : members) {
            if (filter == FilterOption.ALL || filter == FilterOption.HAS_ACTIVE_LOANS && membersWithActiveLoans.contains(member)) {
                filteredMembers.add(member);
            }
        }
        fireTableDataChanged();
    }

    /**
     * An enumeration of the possible filter options for the table.
     */
    public enum FilterOption {
        /**
         * Show all members.
         */
        ALL,

        /**
         * Show only members with active loans.
         */
        HAS_ACTIVE_LOANS,
    }
}
