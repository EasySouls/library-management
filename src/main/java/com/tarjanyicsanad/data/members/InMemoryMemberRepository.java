package com.tarjanyicsanad.data.members;

import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryMemberRepository implements MemberRepository {
    private final ArrayList<Member> members;

    @Inject
    public InMemoryMemberRepository(List<Member> members) {
        this.members = new ArrayList<>(members);
    }

    @Override
    public void addMember(Member member) {
        members.add(member);
    }

    @Override
    public Optional<Member> getMember(int id) {
        return members.stream()
                .filter(member -> member.id() == id)
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return members;
    }

    @Override
    public void removeMember(int id) throws MemberNotFoundException {
        boolean success = members.removeIf(member -> member.id() == id);
        if (!success) {
            throw new MemberNotFoundException("Member with id " + id + " not found");
        }
    }

    @Override
    public void updateMember(Member member) throws MemberNotFoundException {
        removeMember(member.id());
        addMember(member);
    }
}
