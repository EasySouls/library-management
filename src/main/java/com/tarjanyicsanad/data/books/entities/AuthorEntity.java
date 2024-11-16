package com.tarjanyicsanad.data.books.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity(name = "authors")
public class AuthorEntity {

    @Id Integer id;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    LocalDate birthDate;

    public AuthorEntity() {}

    public AuthorEntity(Integer id, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public @NotNull String getFirstName() {
        return firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public @NotNull LocalDate getBirthDate() {
        return birthDate;
    }
}
