package mjwall.http;

import java.io.PrintWriter;

public class HttpClient {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        int runsPerTest = 0;
        if (args.length > 0) {
            runsPerTest = Integer.parseInt(args[0]);
        }
        new HttpTester().run(out, runsPerTest);
        out.close();
    }
}