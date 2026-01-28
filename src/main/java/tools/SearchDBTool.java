package tools;
import common.Utils;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import librarian.Temp;
import model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@ApplicationScoped
public class SearchDBTool {

    private static final Logger log = LoggerFactory.getLogger(SearchDBTool.class);

    @Inject
    Utils utils;

    @Tool("Checks DB. Returns a JSON object with the ISBN if not found.")
    public Temp searchLocalDatabase(List<String> isbns) {
        if (isbns == null || isbns.isEmpty()) {
            log.info("Empty List");
            return new Temp("noAdequateBook");
        }
        boolean newBook = false;
        for(String isbn : isbns) {
            log.info("Inside search DB tool with isbn {}.", isbn);
            String cleanIsbn = utils.cleanIsbn(isbn);
            boolean exists = Book.find("isbn", cleanIsbn).firstResultOptional().isPresent();
            log.info("ISBN {} exists in database? {}.", cleanIsbn, exists);
            if (!exists) {
                newBook = true;
                return new Temp(isbn);
            }
        }
        return new Temp("allBooksInDB");
    }

}
