package mjwall.rmiSameJVM;

import mjwall.rest.OneUpResponse;
import mjwall.rest.RestAddLocal;
import mjwall.testHarness.TestHarness;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LocalTester extends TestHarness {
    private RestAddLocal restAddBean = null;
    
    public String getDescription() {
        return "Local RMI Test on same JVM";
    }
    
    public void setup() {
        final String jndiName = "mjwall/RestAddBean/local";
        Object obj = null;
        try {
            obj = new InitialContext().lookup(jndiName);
        } catch (NamingException e) {
            System.out.println("Error " +e.getMessage());
        }
       
        restAddBean = (RestAddLocal) obj;
    }
    
    public void cleanup() {
        restAddBean = null;
    }
    
    public void runSpecificNumber(int number) {
         int current = 0;
         for (int x=0; x < number; x++ ) {
             current = restAddBean.addOne(current).getResult();
         }
    }
}
