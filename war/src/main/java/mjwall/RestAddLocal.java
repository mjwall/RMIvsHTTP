package mjwall;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


/**
 * Local interface that exposes the RestAddBean with REST endpoints
 */

@Local
@Path("/rest")
public interface RestAddLocal {

    @GET
    @Path("/add/one")
    @Produces("application/json")
    Response getParams(@QueryParam("to") int start);
}