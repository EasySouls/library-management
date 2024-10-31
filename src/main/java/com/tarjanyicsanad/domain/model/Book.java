package com.tarjanyicsanad.domain.model;

import java.time.LocalDate;

public record Book(
        String name,
        String description,
        String author,
        LocalDate publishingDate
) {

}
