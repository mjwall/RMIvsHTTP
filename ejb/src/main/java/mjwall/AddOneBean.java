package mjwall;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Simple EJB to add one to the passed in int
 */
@Stateless
@Local(AddOneLocal.class)
@LocalBinding(jndiBinding="mjwall/AddOneBean/local")
@Remote(AddOneRemote.class)
@RemoteBinding(jndiBinding="mjwall/AddOneBean/remote")
public class AddOneBean implements AddOneLocal, AddOneRemote {
    @Override
    public int addOne(int start) {
        return start + 1;
    }
}
