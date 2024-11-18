package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.exceptions.BookNotFoundException;
import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Member;

import java.util.List;
import java.util.Optional;

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
     * Get all members from the repository
     * @return a list of all members
     */
    List<Member> getAllMembers();

    /**
     * Remove a member from the repository
     * @param id the id of the member to remove
     * @throws MemberNotFoundException if the member with the given id is not found
     */
    void removeMember(int id) throws MemberNotFoundException;

    /**
     * Updates the member with the same id as the given member.
     * @throws MemberNotFoundException if no member with the same id is found
     */
    void updateMember(Member member) throws MemberNotFoundException;

}
