import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import model.CatalogResponseData;
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
    public String testCatalogTool(@QueryParam("topic") String topic,
                                  @QueryParam("genre") String genre,
                                  @QueryParam("language") String language,
                                  @QueryParam("audience") String audience) {
        return librarian.testSearch(topic, genre, language, audience);
    }

    @GET
    @Path("/call-catalog-tool")
    public List<CatalogResponseData> callCatalogTool(@QueryParam("topic") String topic,
                                                     @QueryParam("genre") String genre,
                                                     @QueryParam("language") String language,
                                                     @QueryParam("audience") String audience) {
        return catalogTool.searchOnlineCatalog(topic, genre, language, audience);
    }
}
