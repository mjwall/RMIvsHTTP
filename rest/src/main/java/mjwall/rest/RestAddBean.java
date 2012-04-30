package mjwall.rest;

import mjwall.bean.AddOneLocal;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

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
    public String addOne(int start) {
        return String.valueOf(addOneBean.addOne(start));
    }
}
