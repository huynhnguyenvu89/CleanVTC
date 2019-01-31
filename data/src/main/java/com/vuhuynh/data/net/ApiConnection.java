package com.vuhuynh.data.net;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * API Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can return a value
 */
public class ApiConnection implements Callable<String>{

    private final String CONTENT_TYPE = "Content-Type";
    private final String CONTENT_TYPE_JSON_VALUE = "application/json; charset=utf-8";

    private String response;
    private URL url;

    private ApiConnection(String urlString) throws MalformedURLException {
        this.url = new URL(urlString);
    }

    public static ApiConnection createGET(String urlString) throws MalformedURLException {
        return new ApiConnection(urlString);
    }

    /**
     * Do a request to an API synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response.
     */
    String requestSyncCall(){
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
        okHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);

        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)
                .get().build();

        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public String call() throws Exception {
        return requestSyncCall();
    }
}
