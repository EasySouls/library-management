package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Member;

import java.util.List;
import java.util.Optional;

/**
 * A repository for managing {@link Member}s.
 */
public interface MemberRepository {
    /**
     * Add a new member to the repository
     * @param member the member to add
     */
    void addMember(Member member);

    /**
     * Returns a member with the given id.
     * @param id the id of the member to return
     * @return an empty optional if no member with the given id is found
     */
    Optional<Member> getMember(int id);

    /**
     * Returns a member with the given email.
     * @param email the email of the member to return
     * @throws MemberNotFoundException if no member with the given email is found
     * @return the member with the given email
     */
    Member findMemberByEmail(String email) throws MemberNotFoundException;

    /**
     * Returns a list of members with active loans.
     * @return a list of members with active loans
     */
    List<Member> findMembersWithActiveLoans();

    /**
     * Get all members from the repository
     * @return a list of all members
     */
    List<Member> findAllMembers();

    /**
     * Remove a member from the repository
     * @param id the id of the member to remove
     * @throws MemberNotFoundException if the member with the given id is not found
     */
    void removeMember(int id) throws MemberNotFoundException;

    /**
     * Updates the member with the same id as the given member.
     * @param member the member to update
     * @throws MemberNotFoundException if no member with the same id is found
     */
    void updateMember(Member member) throws MemberNotFoundException;

}
