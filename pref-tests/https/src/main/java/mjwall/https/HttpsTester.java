package mjwall.https;

import mjwall.testHarness.TestHarness;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class HttpsTester extends TestHarness {
    private String url = null;
    private HttpClient httpClient = null;
    private ResponseHandler responseHandler = null;
    
    public String getDescription() {
        return "HTTPS Test on external JVM";
    }

    public void setup() {
        url = "https://localhost:8443/rmi-vs-http-rest/rest/add/one?to=";
        httpClient = new DefaultHttpClient();
        InputStream inputStream = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = ClassLoader.getSystemResourceAsStream("server.keystore");
            trustStore.load(inputStream, "changeit".toCharArray());
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
            Scheme scheme = new Scheme("https", 8443, socketFactory);
            httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try { inputStream.close(); } catch (Exception ignore) {}
        }
        responseHandler = new BasicResponseHandler();
    }
    
    public void cleanup() {
        url = null;
        httpClient = null;
        responseHandler = null;
    }
    
    public void runSpecificNumber(int number) {
        int current = 0;
        for (int x=0; x < number; x++ ) {
            HttpGet httpGet = new HttpGet(url + current);
            Object responseBody;
            try {
              responseBody = httpClient.execute(httpGet, responseHandler);
            } catch (Exception e) {
              throw new RuntimeException(e.getMessage());  
            }
            current = Integer.parseInt(responseBody.toString());
        }
        httpClient.getConnectionManager().shutdown();
    }
}
