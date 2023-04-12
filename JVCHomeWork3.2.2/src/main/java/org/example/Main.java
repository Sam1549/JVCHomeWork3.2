package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.*;


import java.net.URL;


public class Main {
    public static final String URI_NASA_WITH_KEY = "https://api.nasa.gov/planetary/apod?api_key=x5EELAGcWErhqa6FdiPJgv9ZsIkLZD5kz5bhTXid";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet httpGet = new HttpGet(URI_NASA_WITH_KEY);

        CloseableHttpResponse response = httpClient.execute(httpGet);

        Nasa nasa = mapper.readValue(response.getEntity().getContent(), Nasa.class);
        String URI = nasa.getUrl();

        try (BufferedInputStream in = new BufferedInputStream(new URL(URI).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("Image.jpg")) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
