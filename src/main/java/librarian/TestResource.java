package librarian;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import model.Book;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import tools.CatalogTool;
import java.util.List;

@ApplicationScoped
@Path("/tests")
public class TestResource {

    @Inject
    Librarian librarian;

    @Inject
    CatalogTool catalogTool;

    @GET
    @Path("/test-catalog-tool")
    public Response testCatalogTool(@QueryParam("topic") String topic,
                                  @QueryParam("genre") String genre,
                                  @QueryParam("language") String language,
                                  @QueryParam("audience") String audience) {
        String resp = librarian.searchBook(topic, genre, language, audience);
        return Response.status(Response.Status.OK).entity(resp).build();
    }

    @GET
    @Path("/call-catalog-tool")
    public List<String> callCatalogTool(@QueryParam("topic") String topic,
                                                     @QueryParam("genre") String genre,
                                                     @QueryParam("language") String language,
                                                     @QueryParam("audience") String audience) {
        return catalogTool.searchOnlineCatalog(topic, genre, language, audience);
    }

    @POST
    @Path("/books")
    @Transactional
    public Response persistBook(@RequestBody Book book) {
        try{
            book.persist();
            return Response.status(Response.Status.CREATED).entity(book).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
