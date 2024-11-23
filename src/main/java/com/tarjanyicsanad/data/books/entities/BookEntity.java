package com.tarjanyicsanad.data.books.entities;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.authors.entities.AuthorEntity_;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity(name = "books")
public class BookEntity {
    @Id
    int id;

    @Basic(optional = false)
    String title;

    @Basic(optional = false)
    String description;

    @NotNull
    String author;

    @ManyToMany(mappedBy = AuthorEntity_.BOOKS)
    Set<AuthorEntity> authors;

    public BookEntity() {}

    public BookEntity(int id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public @NotNull String getAuthor() {
        return author;
    }
}
