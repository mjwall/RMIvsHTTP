package mjwall.rmiExternalJVM;

import java.io.PrintWriter;

public class RemoteClient {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        new RemoteTester().run(out);
        out.close();
    }
}