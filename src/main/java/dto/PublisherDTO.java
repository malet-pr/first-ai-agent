package dto;

import java.util.List;

public record PublisherDTO(
    String name,
    List<String> locations
) { }
