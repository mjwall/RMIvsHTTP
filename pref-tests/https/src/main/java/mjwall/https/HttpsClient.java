package mjwall.https;

import java.io.PrintWriter;

public class HttpsClient {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        int runsPerTest = Integer.parseInt(args[0]);
        new HttpsTester().run(out, runsPerTest);
        out.close();
    }
}
