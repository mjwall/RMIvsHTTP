package mjwall.rmiSameJVM;

import mjwall.rest.RestAddRemote;
import mjwall.testHarness.TestHarness;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RemoteTester extends TestHarness{
    public String getDescription() {
        return "Remote RMI Test on same JVM";
    }
    
    public void runSpecificNumber(int number) {
         final String jndiName = "mjwall/RestAddBean/remote";
         Object obj = null;
         try {
             obj = new InitialContext().lookup(jndiName);
         } catch (NamingException e) {
             System.out.println("Error " +e.getMessage());
         }
        
         RestAddRemote restAddBean = (RestAddRemote) obj;
         int current = 0;
         for (int x=0; x < number; x++ ) {
             current = Integer.parseInt(restAddBean.addOne(current));
         }
    }
}
