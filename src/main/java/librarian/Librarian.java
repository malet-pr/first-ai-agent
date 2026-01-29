package librarian;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;
import dto.BookDTO;
import org.eclipse.microprofile.faulttolerance.Retry;
import tools.CatalogTool;
import tools.SearchDBTool;

import java.time.temporal.ChronoUnit;

@RegisterAiService(tools = {CatalogTool.class, SearchDBTool.class})
public interface Librarian {

    @Retry(maxRetries = 3, delay = 2, delayUnit = ChronoUnit.SECONDS)
    @SystemMessage(SYSTEM_MESSAGE)
    @UserMessage(USER_MESSAGE)
    String searchBook(@V("topic") String topic,
                      @V("genre") String genre,
                      @V("language") String language,
                      @V("audience") String audience);

    @Retry(maxRetries = 3, delay = 2, delayUnit = ChronoUnit.SECONDS)
    @SystemMessage(SYSTEM_MESSAGE_CREATIVE)
    @UserMessage(USER_MESSAGE_CREATIVE)
    BookDTO createBook(@V("topic") String topic,
                       @V("genre") String genre,
                       @V("language") String language,
                       @V("audience") String audience);

    @Retry(maxRetries = 3, delay = 2, delayUnit = ChronoUnit.SECONDS)
    @SystemMessage(SYSTEM_MESSAGE_CREATIVE)
    @UserMessage(USER_MESSAGE_CREATIVE)
    Multi<String> watchBookBeCreated(
            @V("topic") String topic,
            @V("genre") String genre,
            @V("language") String language,
            @V("audience") String audience
    );

    //////////////////////////////////////////////

    final String SYSTEM_MESSAGE = """
    - TASK: Find ONE new book.
    - PROCESS:
      1. Get up to 3 ISBNs from the catalog. DO NOT fill null or empty parameters.
      2. For the first ISBN, call `searchLocalDatabase`.
      3. If the result contains a number, STOP and return that isbn immediately.
      4. Only if it returns "ALREADY_EXISTS", try the next ISBN.
    - NEVER repeat a search for the same ISBN.
    - Output format: Return ONLY the ISBN string. No sentences. No introductions.
      If the searchCatalog tool returns an empty list, you MUST return the string 'NOT_FOUND' and nothing else. 
      Never guess an ISBN.
    """;

    final String USER_MESSAGE = """
    Search for books about {{topic}} with genre {{genre}},
    language {{language}}, audience {{audience}}.
    """;

    final String SYSTEM_MESSAGE_CREATIVE = """
    You are a professional multilingual librarian. 
    If the languege is not specified, default to English. 
    If the audience is not specified, default to general audience.
    If the language is not english, use it for title, summary, and maybe (not necessarily) Authors, Publishers, Awards, and Reviews.
    Pages, summary, and coverArt can be null in about 5% of the cases. 
    Awards will be an empty list in most cases, about 15% of the time there will be fictional awards.
    Reviews can be empty lists too, but will usually have 2 - 5 fictional reviews.
    Output strictly valid JSON. Keep all keys in english, regardless of the language specified.
    Use lowerCamelCase for all keys, for example first_name should be firstName.
    "IMPORTANT: If a field has no data (like awards or reviews), return an empty list [] or null. DO NOT return a plain string or a URL in place of a structured object."
    """;

    final String USER_MESSAGE_CREATIVE = """
    Create a fictional bookDTO about {{topic}}, with genre {{genre}},
    written in {{language}} for a {{audience}} audience.
    """;
}
