package mjwall.rmiExternalJVM;

import mjwall.rest.RestAddBean;
import mjwall.rest.RestAddRemote;
import mjwall.testHarness.TestHarness;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RemoteTester extends TestHarness {
    private RestAddRemote restAddBean = null;
    
    public String getDescription() {
        return "Remote RMI Test on external JVM";
    }
    
    public void setup() {
        final String jndiName = "mjwall/RestAddBean/remote";
        Object obj = null;
        try {
            obj = new InitialContext().lookup(jndiName);
        } catch (NamingException e) {
            System.out.println("Error " +e.getMessage());
        }
       
        restAddBean = (RestAddRemote) obj;
    }
    
    public void cleanup() {
        restAddBean = null;
    }
    
    public void runSpecificNumber(int number) {
         int current = 0;
         for (int x=0; x < number; x++ ) {
             current = Integer.parseInt(restAddBean.addOne(current));
         }
    }
}
