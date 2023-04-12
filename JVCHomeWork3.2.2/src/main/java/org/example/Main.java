package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.*;




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



        CloseableHttpResponse response = httpClient.execute(new HttpGet(URI_NASA_WITH_KEY));

        Nasa nasa = mapper.readValue(response.getEntity().getContent(), Nasa.class);
        String URI = nasa.getUrl();

        CloseableHttpResponse pictureResponse = httpClient.execute(new HttpGet(nasa.getUrl()));
        String[] arr = nasa.getUrl().split("/");
        String file = arr[6];
        HttpEntity entity = pictureResponse.getEntity();
        if (entity != null) {
            FileOutputStream fos = new FileOutputStream(file);
            entity.writeTo(fos);
            fos.close();
        }

    }


}
