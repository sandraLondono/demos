package utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ZAPUtils {

    private static final String ZAP_API_URL = "http://localhost:8080";
    private static final String ZAP_API_KEY = "tkmav0loqjguesciv5u797h61n";

    public static String startScan(String targetUrl) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(ZAP_API_URL + "/JSON/ascan/action/scan/");
            request.addHeader("Accept", "application/json");
            request.addHeader("X-ZAP-API-Key", ZAP_API_KEY);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("url", targetUrl));
            params.add(new BasicNameValuePair("recurse", "true"));

            request.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(request);

            String json = EntityUtils.toString(response.getEntity());
            DocumentContext jsonObject = JsonPath.parse(json);
            return jsonObject.read("scan").toString();

        }
    }

    public static void generateReport(String format, String path) throws Exception {
        String reportUrl = "http://localhost:8080/OTHER/core/other/" + format + "report/?";
        String reportPath = path + "zap-report." + format;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(reportUrl + "apikey=" + ZAP_API_KEY);

            byte[] reportBytes = client.execute(request, response ->
                    response.getEntity().getContent().readAllBytes()
            );

            try (FileOutputStream fos = new FileOutputStream(reportPath)) {
                fos.write(reportBytes);
            }
        }
    }

    public static void waitForScanToFinish(String scanId) throws Exception {
        String status = "0";
        while (!status.equals("100")) {
            Thread.sleep(5000);
            HttpGet request = new HttpGet("http://localhost:8080/JSON/ascan/view/status/?scanId=" + scanId);
            request.addHeader("X-ZAP-API-Key", ZAP_API_KEY);

            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(request)) {

                String json = EntityUtils.toString(response.getEntity());
                DocumentContext result = JsonPath.parse(json);
                status = result.read("$.status").toString();
                System.out.println("Estado del escaneo: " + status + "%");
            }
        }
        System.out.println("✅ Escaneo completado.");
    }

}
