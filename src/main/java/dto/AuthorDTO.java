package dto;

import java.time.LocalDate;

public record AuthorDTO(
    String firstName,
    String lastName,
    LocalDate birthdate,
    String nationality
) { }
