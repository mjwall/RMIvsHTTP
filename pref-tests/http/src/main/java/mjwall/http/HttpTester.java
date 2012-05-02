package mjwall.http;

import mjwall.testHarness.TestHarness;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

public class HttpTester extends TestHarness {
    
    private String url = null;
    private HttpClient httpClient = null;
    private ResponseHandler responseHandler = null;
    private DocumentBuilder db = null;
    
    public String getDescription() {
        return "HTTP Test on external JVM";
    }
    
    public void setup() {
        url = "http://localhost:8080/rmi-vs-http-war/rest/add/one?to=";
        httpClient = new DefaultHttpClient();
        responseHandler = new BasicResponseHandler();
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public void cleanup() {
        url = null;
        httpClient.getConnectionManager().shutdown();
        httpClient = null;
        responseHandler = null;
        db = null;
    }
    
    public void runSpecificNumber(int number) {
        int current = 0;
        for (int x=0; x < number; x++ ) {
            HttpGet httpGet = new HttpGet(url + current);
            Object responseBody;
            Document document = null;
            try {
                responseBody = httpClient.execute(httpGet, responseHandler);
                document = db.parse(new InputSource(new StringReader(responseBody.toString())));
            } catch (Exception e) {
              throw new RuntimeException(e.getMessage());  
            }
            // maybe there is a better way to parse this?
            current = Integer.parseInt(document.getDocumentElement().getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
        }
    }
}
