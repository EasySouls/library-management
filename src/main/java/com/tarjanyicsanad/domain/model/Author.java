package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;

import java.io.Serializable;
import java.time.LocalDate;

public record Author(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth
) implements Serializable {
    public String fullName() {
        return firstName + " " + lastName;
    }

    public int age() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public AuthorEntity toEntity() {
        return new AuthorEntity(id, firstName, lastName, dateOfBirth);
    }

    public static Author fromEntity(AuthorEntity entity) {
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate());
    }
}
