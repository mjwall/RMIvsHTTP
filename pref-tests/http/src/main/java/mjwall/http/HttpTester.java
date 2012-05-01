package mjwall.http;

import mjwall.testHarness.TestHarness;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpTester extends TestHarness {
    public String getDescription() {
        return "HTTP Test on external JVM";
    }
    
    public void runSpecificNumber(int number) {
        final String url = "http://localhost:8080/rmi-vs-http-rest/rest/add/one?to=";
        HttpClient httpClient = new DefaultHttpClient();
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
