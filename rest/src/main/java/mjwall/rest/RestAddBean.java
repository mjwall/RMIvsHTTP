package mjwall.rest;

import mjwall.bean.AddOneLocal;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

/**
 * EJB to expose REST endpoint
 */
@Stateless
@Local(RestAddLocal.class)
@LocalBinding(jndiBinding="mjwall/RestAddBean/local")
@Remote(RestAddRemote.class)
@RemoteBinding(jndiBinding="mjwall/RestAddBean/remote")
public class RestAddBean implements RestAddLocal, RestAddRemote {
    @EJB(mappedName = "mjwall/AddOneBean/local")
    AddOneLocal addOneBean;
    
    @Override
    public OneUpResponse addOne(int start) {
        OneUpResponse response = new OneUpResponse();
        response.setResult(addOneBean.addOne(start));
        return response;
    }
}
