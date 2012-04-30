package mjwall;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.PrintWriter;

public class RemoteTester {
    final int numberOfRuns = 10000;
    
    public void run(PrintWriter out) {
        long startTime = System.currentTimeMillis();
        
        final String jndiName = "mjwall/AddOneBean/remote";
        Object obj = null;
        try {
            obj = new InitialContext().lookup(jndiName);
        } catch (NamingException e) {
            out.println("Error " +e.getMessage());
        }
    
        AddOneRemote addOneBean = (AddOneRemote) obj;
        int times = 0;
        for (int x=0; x < numberOfRuns; x++ ) {
            times = addOneBean.addOne(times);
            //out.println(times);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        out.println("via remote JNDI interface, addOne method called " + times + " times took " + elapsedTime + " millis");
    }
}
