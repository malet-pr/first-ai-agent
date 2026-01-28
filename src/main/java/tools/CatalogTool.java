package tools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.BookSearchCache;
import dev.langchain4j.agent.tool.Tool;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import dto.CatalogResponseData;
import dto.CatalogResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CatalogTool {

    private static final Logger log = LoggerFactory.getLogger(CatalogTool.class);
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Tool("Search a real online catalog for existing books.")
    public List<CatalogResponseData> searchOnlineCatalog(String topic, String genre, String language, String audience) {
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(URLEncoder.encode(topic, StandardCharsets.UTF_8));
            if (genre != null && !genre.isEmpty()) {
                queryBuilder.append("+subject:").append(URLEncoder.encode(genre, StandardCharsets.UTF_8));
            } if (audience != null && !audience.isEmpty()) {
                queryBuilder.append("+subject:(").append(URLEncoder.encode(audience, StandardCharsets.UTF_8)).append(")");
            }
            if(language != null && !language.isEmpty()) {
                queryBuilder.append("&lan=").append(language);
            }
            queryBuilder.append("&fields=title,author_name,subject,isbn&sort=random&limit=3");
            String finalQuery = queryBuilder.toString().replace("+", "%20");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openlibrary.org/search.json?q=" + finalQuery))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            CatalogResponseWrapper wrapper = objectMapper.readValue(response.body(), CatalogResponseWrapper.class);
            List<CatalogResponseData> results = wrapper.docs().stream()
                    .filter(doc -> doc.isbn() != null && doc.isbn().length > 0)
                    .map(doc -> {
                        String isbn13 = Arrays.stream(doc.isbn())
                                .filter(i -> i.startsWith("978") || i.startsWith("979"))
                                .findFirst()
                                .orElse(null);
                        String[] relevant = Arrays.stream(doc.subject())
                                .filter(s -> s.toLowerCase().contains(topic.toLowerCase()) ||
                                        s.toLowerCase().contains(genre.toLowerCase()))
                                .limit(3)
                                .toArray(String[]::new);
                        return new CatalogResponseData(doc.title(), doc.author_name(), relevant, new String[]{isbn13});
                    })
                    .filter(doc -> doc.isbn()[0] != null)
                    .limit(3)
                    .toList();
            return results;
        } catch (Exception e) {
            log.error("Could not reach the catalog: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}