package dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
import java.util.List;

public record BookDTO(
    String title,
    List<AuthorDTO> authorDTOS,
    List<PublisherDTO> publisherDTOS,
    LocalDate publicationDate,
    String genre,
    Integer pages,
    String language,
    @JsonPropertyDescription("International Standard BookDTO Number")
    String isbn,
    @JsonPropertyDescription("A brief summary of the book's content")
    String summary,
    CoverArtDTO coverArtDTO,
    List<AwardDTO> awardDTOS,
    List<ReviewDTO> reviewDTOS
) { }

