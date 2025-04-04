package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.RequestClient;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.InternalServerException;
import com.github.gmarcg00.spotify.exception.NetworkConnectionException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.utils.AbstractJsonBodyHandler;
import com.github.gmarcg00.spotify.utils.JsonObjectBodyHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Executor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final RequestClient client;

    public Executor(){
        this.client = RequestClient.getInstance();
    }

    public <T> T get(String path, String token, Class<T> responseType) throws UnauthorizedException, EntityNotFoundException {
        HttpRequest request = createGetRequest(path,token);
        HttpResponse<T> response = doGet(request,new JsonObjectBodyHandler<>(responseType));
        checkResponse(response);
        return response.body();
    }

    private HttpRequest createGetRequest(String path,String token){
        return HttpRequest.newBuilder()
                .uri(URI.create(path))
                .header(AUTHORIZATION_HEADER, BEARER_PREFIX + token)
                .GET()
                .build();
    }

    private <T> HttpResponse<T> doGet(HttpRequest request, AbstractJsonBodyHandler<T> bodyHandler){
        try {
            return client.getClient().send(request, bodyHandler);
        } catch (IOException e) {
            throw new NetworkConnectionException(e.getMessage());
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new InternalServerException(e.getMessage());
        }
    }

    private <T> void checkResponse(HttpResponse<T> response) throws EntityNotFoundException, UnauthorizedException {
        switch (response.statusCode()){
            case HttpURLConnection.HTTP_UNAUTHORIZED :
                throw new UnauthorizedException("INVALID_ACCESS_TOKEN");
            case HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_NOT_FOUND:
                throw new EntityNotFoundException("Entity with id: %s not found");
            default:
                break;
        }
    }
}
