package com.tarjanyicsanad.domain.model;

import com.tarjanyicsanad.data.books.entities.BookEntity;

import java.io.Serializable;
import java.time.LocalDate;

public record Book(
        int id,
        String title,
        String description,
        String author,
        LocalDate publishingDate
) implements Serializable {
    public BookEntity toEntity() {
        return new BookEntity(id, title, description, author);
    }

    public static Book fromEntity(BookEntity entity) {
        return new Book(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getAuthor(), null);
    }
}
