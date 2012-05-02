package mjwall.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Remote interface to RestAddBean
 */
@Path("/rest")
public interface RestAddRemote {

    @GET
    @Path("/add/one")
    @Produces("application/xml")
    OneUpResponse addOne(@QueryParam("to") int start);

}
