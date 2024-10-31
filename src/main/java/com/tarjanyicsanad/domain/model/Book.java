package com.tarjanyicsanad.domain.model;

import java.time.LocalDate;

public record Book(
        int id,
        String title,
        String description,
        String author,
        LocalDate publishingDate
) {

}
