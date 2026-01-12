import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;
import model.Book;

@RegisterAiService
public interface Librarian {
    @SystemMessage(SYSTEM_MESSAGE)
    @UserMessage(USER_MESSAGE)
    Book createBook(@V("topic") String topic,
                             @V("genre") String genre,
                             @V("language") String language,
                             @V("audience") String audience);

    @SystemMessage(SYSTEM_MESSAGE)
    @UserMessage(USER_MESSAGE)
    Multi<String> watchBookBeCreated(
            @V("topic") String topic,
            @V("genre") String genre,
            @V("language") String language,
            @V("audience") String audience
    );

    //////////////////////////////////////////////

    final String SYSTEM_MESSAGE = """
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

    final String USER_MESSAGE = """
    Create a fictional book about {{topic}}, with genre {{genre}},
    written in {{language}} for a {{audience}} audience.
    """;
}

