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
    public String getDescription() {
        return "HTTPS Test on external JVM";
    }
    
    public void runSpecificNumber(int number) {
        final String url = "https://localhost:8443/rmi-vs-http-rest/rest/add/one?to=";
        HttpClient httpClient = new DefaultHttpClient();
        KeyStore trustStore;
        InputStream inputStream = null;
        Scheme scheme = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = ClassLoader.getSystemResourceAsStream("server.keystore");
            trustStore.load(inputStream, "changeit".toCharArray());
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
            scheme = new Scheme("https", 8443, socketFactory);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try { inputStream.close(); } catch (Exception ignore) {}
        }
        httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
        ResponseHandler responseHandler = new BasicResponseHandler();
        
        
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
