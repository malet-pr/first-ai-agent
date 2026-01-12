package model;

import java.time.LocalDate;

public record Author(
    String firstName,
    String lastName,
    LocalDate birthdate,
    String nationality
) { }
