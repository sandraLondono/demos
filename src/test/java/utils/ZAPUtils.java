package utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;

public class ZAPUtils {

    private static final String ZAP_API_URL = "http://localhost:8080";
    private static final String ZAP_API_KEY = "tkmav0loqjguesciv5u797h61n";

    public static void startScan(String targetUrl) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(ZAP_API_URL + "/JSON/ascan/action/scan/");
            request.addHeader("Accept", "application/json");
            request.addHeader("X-ZAP-API-Key", ZAP_API_KEY);
            request.setEntity(new StringEntity("url=" + targetUrl + "&recurse=true"));

            client.execute(request);
        }
    }

    public static void generateReport(String scanId, String format, String path) throws Exception {
        String reportUrl = "http://localhost:8080/OTHER/core/other/" + format + "report/?";
        String reportPath = path + "zap-report." + format;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(reportUrl + "apikey=tu_api_key");

            byte[] reportBytes = client.execute(request, response ->
                    response.getEntity().getContent().readAllBytes()
            );

            try (FileOutputStream fos = new FileOutputStream(reportPath)) {
                fos.write(reportBytes);
            }
        }
    }

}
