package com.tarjanyicsanad.data.members.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity(name = "members")
public class MemberEntity {

    @Id Integer id;

    String name;

    String email;

    LocalDate joinedAt;

    public MemberEntity() {}

    public MemberEntity(Integer id, String name, String email, LocalDate joinedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }
}
