package com.opensabre.sample;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SampleApplicationTests {
    String httpUrl = "http://192.168.5.5:8090/user/admin123";

    @Test
    public void httpclient() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
//                .setProxy(new HttpHost("127.0.0.1", 9788))
                .build();
        String res = "";
        HttpGet request = new HttpGet(httpUrl);
        request.setConfig(RequestConfig.custom()
                .setSocketTimeout(-1)
                .setConnectTimeout(-1)
                .setExpectContinueEnabled(false)
                .build());

        HttpResponse response = httpClient.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            //返回json格式
            res = EntityUtils.toString(response.getEntity());
        }
        System.out.println(res);
    }
}
