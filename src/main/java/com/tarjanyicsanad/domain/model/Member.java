package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.members.entities.MemberEntity;

import java.io.Serializable;
import java.time.LocalDate;

public record Member(
        Integer id,
        String name,
        String email,
        LocalDate joinedAt
) implements Serializable {

    public MemberEntity toEntity() {
        return new MemberEntity(id, name, email, joinedAt);
    }

    public static Member fromEntity(MemberEntity entity) {
        return new Member(entity.getId(), entity.getName(), entity.getEmail(), entity.getJoinedAt());
    }
}
