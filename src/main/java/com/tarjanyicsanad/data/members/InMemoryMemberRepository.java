package com.tarjanyicsanad.data.members;

import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An in-memory implementation of the {@link MemberRepository} interface.
 */
public class InMemoryMemberRepository implements MemberRepository {
    private final ArrayList<Member> members;

    /**
     * Creates a new {@link InMemoryMemberRepository}.
     *
     * @param members the initial list of members
     */
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
    public Member findMemberByEmail(String email) throws MemberNotFoundException {
        return members.stream()
                .filter(member -> member.email().equals(email))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("Member with email " + email + " not found"));
    }

    @Override
    public List<Member> findMembersWithActiveLoans() {
        return members.stream()
                .filter(member ->
                        !member.loans().isEmpty() &&
                                member.loans().stream().anyMatch(loan ->
                                        loan.returnDate().isAfter(LocalDate.now())
                                )
                )
                .toList();
    }

    @Override
    public List<Member> findAllMembers() {
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
