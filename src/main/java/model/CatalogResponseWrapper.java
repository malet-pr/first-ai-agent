package model;

import java.util.List;

public record CatalogResponseWrapper(
    int numFound,
    int start,
    boolean numFoundExact,
    int num_found,
    String documentation_url,
    String q,
    Object offset,
    List<CatalogResponseData> docs
) { }
