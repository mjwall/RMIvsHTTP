package mjwall;

import javax.naming.Context;
import javax.naming.InitialContext;

public class RemoteClient {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        
        //JBoss default remote jndi : <ejb-name>/remote
        final String jndiName = "mjwall/AddOneBean/remote";
        Context ic = new InitialContext();
        //System.out.println("about to lookup jndi name " + jndiName);
        Object obj = ic.lookup(jndiName);
        //System.out.println("lookup returned " + obj);

        //System.out.println("calling echo() after cast");
        AddOneRemote addOneRemote = (AddOneRemote) obj;
        int times = 0;
        for (int x=0; x < 1000; x++ ) {
            times = addOneRemote.addOne(times);
            System.out.println(times);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("via remote JNDI interface, addOne method called " + times + " times took " + elapsedTime + " millis");
    }
}