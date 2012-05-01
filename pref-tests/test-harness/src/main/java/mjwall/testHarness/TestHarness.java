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
    // change this if you want a different sample size
    final int numberOfTests = 15;
    long[] dataPoints = new long[numberOfTests];

    public void run(PrintWriter out, int numberOfRunsPerTest) {
        // info printed to System.out.println is to give an indication somethign is running.  It only
        // works when you run from the command line.  For the tests that are in a servlet, the browser spinning
        // is indication enough to wait.
        if (numberOfRunsPerTest == 0 || numberOfRunsPerTest < 0) {
            // default
            numberOfRunsPerTest=10;
        }
        System.out.println("Starting " + numberOfTests + " runs");
        setup();
        for (int r=0; r < numberOfTests; r++) {
            System.out.print(".");
            long startTime = System.currentTimeMillis();
            runSpecificNumber(numberOfRunsPerTest);
            long stopTime = System.currentTimeMillis();
            dataPoints[r] = stopTime - startTime;
        }
        cleanup();
        System.out.println();
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
        // replace this with something informative about the test case
        return null;
    }

    public void setup() {
        // replace this with things like setting up a HTTPClient or getting the
        // initial context.  In a real app, this stuff should be already done when
        // you try to execute some functionality.  We don't want this time included
        // in the total.  Currently is executed for each of the numberOfTests runs, but
        // it could be moved outside of that
     }

    public void cleanup() {
        // cleanup references here
    }

    public void runSpecificNumber(int number) {
        // specific implementation for this test case.  Should loop  through
        // the given number of times and execute the method, do somethign with it
        // and pass that result into the next call.  See specific implementations
        // for more details
   }
}
