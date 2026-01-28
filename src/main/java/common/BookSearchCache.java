package common;

import dto.CatalogResponseData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class BookSearchCache {
    private final Map<String, CatalogResponseData> cache = new ConcurrentHashMap<>();
    @Inject
    Utils utils;

    public void putAll(List<CatalogResponseData> books) {
        books.forEach(b -> cache.put(utils.cleanIsbn(b.isbn()[0]), b));
    }

    public CatalogResponseData get(String isbn) {
        return cache.get(utils.cleanIsbn(isbn));
    }
}
