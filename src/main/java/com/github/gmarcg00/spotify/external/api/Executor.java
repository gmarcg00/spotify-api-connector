package com.github.gmarcg00.spotify.external.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gmarcg00.spotify.RequestClient;
import com.github.gmarcg00.spotify.external.api.model.SpotifyApiErrorResponse;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.InternalServerException;
import com.github.gmarcg00.spotify.exception.NetworkConnectionException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.utils.AbstractJsonBodyHandler;
import com.github.gmarcg00.spotify.utils.GlobalMapper;
import com.github.gmarcg00.spotify.utils.JsonListBodyHandler;
import com.github.gmarcg00.spotify.utils.JsonObjectBodyHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Executor<T> {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final RequestClient client;
    private final String path;

    public Executor(String path){
        this.client = RequestClient.getInstance();
        this.path = path;
    }

    public T get(String id, String market, String token, Class<T> type) throws EntityNotFoundException, UnauthorizedException {
        HttpRequest request = createGetRequest(createPath(id,market),token);
        HttpResponse<T> response = doGet(request,new JsonObjectBodyHandler<>(type));
        checkResponse(response,id);
        return response.body();
    }

    private HttpRequest createGetRequest(String path,String token){
        return HttpRequest.newBuilder()
                .uri(URI.create(path))
                .header(AUTHORIZATION_HEADER, BEARER_PREFIX + token)
                .GET()
                .build();
    }

    private String createPath(String id, String market) {
        String basePath = String.join("/",path, id);
        return (market != null && !market.isEmpty()) ? basePath + "?market=" + market : basePath;
    }

    private HttpResponse<T> doGet(HttpRequest request, AbstractJsonBodyHandler<T> bodyHandler){
        try {
            return client.getClient().send(request, bodyHandler);
        } catch (IOException e) {
            throw new NetworkConnectionException(e.getMessage());
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new InternalServerException(e.getMessage());
        }
    }

    private void checkResponse(HttpResponse<T> response, String id) throws EntityNotFoundException, UnauthorizedException {
        switch (response.statusCode()){
            case HttpURLConnection.HTTP_UNAUTHORIZED :
                throw new UnauthorizedException("INVALID_ACCESS_TOKEN");
            case HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_NOT_FOUND:
                throw new EntityNotFoundException(String.format("Entity with id: %s not found",id));
            default:
                break;
        }
    }

    public List<T> gets(String[] ids, String token) throws EntityNotFoundException {
        String idParam = String.join(",", ids);
        HttpRequest request = createGetRequest(path + "?ids=" + idParam ,token);
        HttpResponse<List<T>> response = doGetWithListResponse(request,new JsonListBodyHandler<>(new TypeReference<>() {}));
        checkResponseList(response);
        return response.body();
    }

    private void checkResponseList(HttpResponse<List<T>> response) throws EntityNotFoundException{
        if(HttpURLConnection.HTTP_BAD_REQUEST == response.statusCode()){
            SpotifyApiErrorResponse errorResponse = GlobalMapper.getInstance()
                    .map(response.body().toString(),SpotifyApiErrorResponse.class);
            throw new EntityNotFoundException(errorResponse.getError().getMessage());
        }
    }

    private HttpResponse<List<T>> doGetWithListResponse(HttpRequest request, AbstractJsonBodyHandler<List<T>> bodyHandler) {
        try {
            return client.getClient().send(request, bodyHandler);
        } catch (IOException e) {
            throw new NetworkConnectionException(e.getMessage());
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new InternalServerException(e.getMessage());
        }
    }

}
