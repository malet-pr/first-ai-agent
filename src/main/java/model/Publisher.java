package model;

import java.util.List;

public record Publisher(
    String name,
    List<String> locations
) { }
