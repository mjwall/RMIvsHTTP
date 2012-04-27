package mjwall;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

/**
 * EJB to expose REST endpoint
 */
@Stateless
public class RestAddBean implements RestAddLocal {
    @EJB(mappedName = "mjwall/AddOneBean/local")
    AddOneLocal addOneBean;
    
    @Override
    public Response getParams(int start) {
        return Response.status(200).entity("{ \"answer\" : " + addOneBean.addOne(start) + "}").build();
    }
}
