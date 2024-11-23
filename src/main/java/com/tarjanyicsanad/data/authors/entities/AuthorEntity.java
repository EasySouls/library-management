package com.tarjanyicsanad.data.authors.entities;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity_;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Entity(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    private String firstName;

    @Basic(optional = false)
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @OneToMany(mappedBy = BookEntity_.AUTHOR, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<BookEntity> books = new HashSet<>();

    public AuthorEntity() {}

    public AuthorEntity(String firstName, String lastName, LocalDate birthDate, Collection<BookEntity> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.books = books;
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

    public Collection<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Collection<BookEntity> books) {
        this.books = books;
    }
}
