package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.members.entities.MemberEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a member with basic details such as name, email, and the date they joined.
 */
public record Member(
        Integer id,
        String name,
        String email,
        LocalDate joinedAt,
        Set<Loan> loans
) implements Serializable {

    /**
     * Converts this Member to a MemberEntity for persistence.
     *
     * @return a MemberEntity representing this Member.
     */
    public MemberEntity toEntity() {
        return new MemberEntity(name, email, joinedAt);
    }

    /**
     * Creates a Member from a MemberEntity.
     *
     * @param entity the MemberEntity to convert.
     * @return a Member representing the given MemberEntity.
     */
    public static Member fromEntity(MemberEntity entity) {
        Set<Loan> loans = entity.getLoans().stream()
                .map(Loan::fromEntityShallow)
                .collect(Set::of, Set::add, Set::addAll);
        return new Member(entity.getId(), entity.getName(), entity.getEmail(), entity.getJoinedAt(), loans);
    }

    public static Member fromEntityShallow(MemberEntity entity) {
        return new Member(entity.getId(), entity.getName(), entity.getEmail(), entity.getJoinedAt(), Set.of());
    }
}
