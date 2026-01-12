package model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
import java.util.List;

public record Book(
    String title,
    List<Author> authors,
    List<Publisher> publishers,
    LocalDate publicationDate,
    String genre,
    Integer pages,
    String language,
    @JsonPropertyDescription("International Standard Book Number")
    String isbn,
    @JsonPropertyDescription("A brief summary of the book's content")
    String summary,
    CoverArt coverArt,
    List<Award> awards,
    List<Review> reviews
) { }

