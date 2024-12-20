package com.tarjanyicsanad.data.authors.entities;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity_;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private Set<BookEntity> books = new HashSet<>();

    public AuthorEntity() {}

    public AuthorEntity(String firstName, String lastName, LocalDate birthDate, Set<BookEntity> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.books = books;
    }

    public AuthorEntity(Integer id, String firstName, String lastName, LocalDate birthDate, Set<BookEntity> books) {
        this.id = id;
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

    public Set<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Set<BookEntity> books) {
        this.books = books;
    }
}
