package com.tarjanyicsanad.domain.model;

import java.time.LocalDate;

public record Author(String firstName, String lastName, LocalDate dateOfBirth) {
}
