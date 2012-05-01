package mjwall.testHarness;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.PrintWriter;

/**
 * Abstract Class for testing performance
 * 
 * 
 */
public abstract class TestHarness {
    // change these to rerun with different amounts
    final int numberOfRunsPerTest = 100000;
    final int numberOfTests = 15;
    long[] dataPoints = new long[numberOfTests]; 
   
    public void run(PrintWriter out) {
        System.out.println("Starting " + numberOfTests + " runs");
        for (int r=0; r < numberOfTests; r++) {
            System.out.print(".");
            long startTime = System.currentTimeMillis();
            runSpecificNumber(numberOfRunsPerTest);
            long stopTime = System.currentTimeMillis();
            dataPoints[r] = stopTime - startTime;
        }
        out.println("Test " + getDescription() + " run " + numberOfTests + " times with " + numberOfRunsPerTest + " in each test");
        long sum=0;
        for (int i=0; i < numberOfTests; i++) {
            sum = sum + dataPoints[i];
            out.println("Run " + (i + 1) + " took " + dataPoints[i] + " milliseconds");
        }
        double average = sum/dataPoints.length;
        out.println("Average time for " + numberOfTests + " runs was " + average);
        double executionsPerSecond = numberOfRunsPerTest / (average * .001);
        out.println("Executions per second: " + executionsPerSecond);
                
    }
    
    
    
    public String getDescription() {
        // replace this in the concrete class
        return null;
    }
        
    public void runSpecificNumber(int number) {
        /*
        final String jndiName = "mjwall/AddOneBean/local";
        Object obj = null;
        try {
            obj = new InitialContext().lookup(jndiName);
        } catch (NamingException e) {
            System.out.println("Error " +e.getMessage());
        }

        AddOneLocal addOneBean = (AddOneLocal) obj;
        int times = 0;
        for (int x=0; x < numberOfRuns; x++ ) {
            times = addOneBean.addOne(times);
        }
        out.println("via local JNDI interface, addOne method called " + times + " times took " + elapsedTime + " millis");
        */
   }
}
