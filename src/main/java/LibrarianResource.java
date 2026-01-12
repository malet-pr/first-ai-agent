import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Book;
import org.jboss.resteasy.reactive.RestQuery;

@ApplicationScoped
@Path("/librarian")
public class LibrarianResource {

    @Inject
    Librarian librarian;

    @GET
    @Path("books")
    public Response crateBook(@QueryParam("topic") String topic,
                             @QueryParam("genre") String genre,
                             @QueryParam("language") String language,
                             @QueryParam("audience") String audience) {
        Book book = librarian.createBook(topic,genre,language,audience);
        return Response.status(Response.Status.OK)
                .entity(book)
                .build();
    }

    @GET
    @Path("/stream/books")
    @Produces(MediaType.SERVER_SENT_EVENTS) // This tells the browser to keep the connection open
    public Multi<String> streamBook(
            @RestQuery String topic,
            @RestQuery String genre,
            @RestQuery String language,
            @RestQuery String audience) {

        return librarian.watchBookBeCreated(topic, genre, language, audience);
    }


}
