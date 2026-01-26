package dto;

public record CatalogResponseData(
     String title,
     String[] author_name,
     String[] subject,
     String[] isbn
) {}
