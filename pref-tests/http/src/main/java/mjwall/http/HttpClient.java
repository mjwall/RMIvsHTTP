package mjwall.http;

import java.io.PrintWriter;

public class HttpClient {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        new HttpTester().run(out);
        out.close();
    }
}