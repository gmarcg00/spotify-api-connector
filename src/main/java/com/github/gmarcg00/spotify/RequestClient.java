package com.github.gmarcg00.spotify;


import java.net.http.HttpClient;

public class RequestClient {

    private static RequestClient instance;
    private final HttpClient client;

    private RequestClient(HttpClient client){
        this.client = client;
    }

    public static RequestClient getInstance(){
        if(instance == null){
            instance = new RequestClient(HttpClient.newHttpClient());
        }
        return instance;
    }

    public HttpClient getClient(){
        return client;
    }
}
