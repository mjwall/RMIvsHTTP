package mjwall.rmiSameJVM;

import mjwall.rest.RestAddLocal;
import mjwall.testHarness.TestHarness;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LocalTester extends TestHarness {
    public String getDescription() {
        return "Local RMI Test on same JVM";
    }
    
    public void runSpecificNumber(int number) {
         final String jndiName = "mjwall/RestAddBean/local";
         Object obj = null;
         try {
             obj = new InitialContext().lookup(jndiName);
         } catch (NamingException e) {
             System.out.println("Error " +e.getMessage());
         }
        
         RestAddLocal restAddBean = (RestAddLocal) obj;
         int current = 0;
         for (int x=0; x < number; x++ ) {
             current = Integer.parseInt(restAddBean.addOne(current));
         }
    }
}
