package com.tarjanyicsanad.data.authors.entities;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "authors")
public class AuthorEntity {

    @Id
    Integer id;

    @Basic(optional = false)
    String firstName;

    @Basic(optional = false)
    String lastName;

    @NotNull
    LocalDate birthDate;

    @ManyToMany()
    Set<BookEntity> books;

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
