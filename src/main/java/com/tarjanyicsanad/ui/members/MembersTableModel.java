package com.tarjanyicsanad.ui.members;

import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.Optional;

public class MembersTableModel extends AbstractTableModel {

    private final transient MemberRepository memberRepository;

    public MembersTableModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void removeMember(int id) {
        memberRepository.removeMember(id);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return memberRepository.findAllMembers().size();
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
}
