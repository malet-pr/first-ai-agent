package tools;
import common.Utils;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import librarian.Temp;
import model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SearchDBTool {

    private static final Logger log = LoggerFactory.getLogger(SearchDBTool.class);

    @Inject
    Utils utils;


    @Tool("Checks DB. Returns the ISBN if not found.")
    public String searchLocalDatabase(String isbn) {
        log.info("Searching for ISBN: {}", isbn);
        String cleanIsbn = utils.cleanIsbn(isbn);
        boolean exists = Book.find("isbn", cleanIsbn).firstResultOptional().isPresent();
        if (!exists) {
            log.info("MATCH! Returning SUCCESS for {}", cleanIsbn);
            return isbn;
        }
        return "ALREADY_EXISTS";
    }
}
