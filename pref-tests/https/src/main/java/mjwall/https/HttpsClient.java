package mjwall.https;

import java.io.PrintWriter;

public class HttpsClient {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        new HttpsTester().run(out);
        out.close();
    }
}
