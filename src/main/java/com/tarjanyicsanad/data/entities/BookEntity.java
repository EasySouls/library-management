package com.tarjanyicsanad.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity(name = "books")
public class BookEntity {
    @Id
    int id;

    @NotNull
    String title;

    String description;

    @NotNull
    String author;

    public BookEntity() {}

    public BookEntity(int id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }
}
