package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.books.entities.AuthorEntity;

import java.time.LocalDate;

public record Author(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth
) {
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
