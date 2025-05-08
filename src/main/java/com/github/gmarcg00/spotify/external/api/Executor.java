package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.model.exception.SpotifyApiErrorResponse;
import com.github.gmarcg00.spotify.external.api.model.exception.SpotifyAuthErrorResponse;
import com.github.gmarcg00.spotify.utils.GlobalMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Executor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String APPLICATION_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String RATE_LIMIT_EXCEPTION_MESSAGE = "Rate limit exceeded. Please try it again later.";

    private final HttpClient httpClient;
    private final GlobalMapper globalMapper;

    public Executor(){
        this.httpClient = HttpClient.newHttpClient();
        this.globalMapper = new GlobalMapper();
    }

    public <T> T get(String path, String token, Class<T> responseType) throws SpotifyApiException{
        HttpRequest request = createGetRequest(path,token);
        HttpResponse<String> response = doRequest(request,HttpResponse.BodyHandlers.ofString());
        checkResponse(response);
        return globalMapper.map(response.body(),responseType);
    }

    private HttpRequest createGetRequest(String path,String token){
        return HttpRequest.newBuilder()
                .uri(URI.create(path))
                .header(AUTHORIZATION_HEADER, BEARER_PREFIX + token)
                .GET()
                .build();
    }

    public <T> T post(String path, String body, Class<T> responseType) throws BadRequestException {
        HttpRequest request = createPostRequest(path,body);
        HttpResponse<String> response = doRequest(request,HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 400){
            SpotifyAuthErrorResponse responseError = globalMapper.map(response.body(),SpotifyAuthErrorResponse.class);
            throw new BadRequestException(responseError.getErrorDescription());
        }
        return globalMapper.map(response.body(),responseType);
    }

    private HttpRequest createPostRequest(String path, String body){
        return HttpRequest.newBuilder()
                .uri(URI.create(path))
                .header(CONTENT_TYPE_HEADER, APPLICATION_URL_ENCODED)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    private HttpResponse<String> doRequest(HttpRequest request, HttpResponse.BodyHandler<String> bodyHandler){
        try {
            return httpClient.send(request, bodyHandler);
        } catch (IOException e) {
            throw new NetworkConnectionException(e.getMessage());
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new InternalServerException(e.getMessage());
        }
    }

    private void checkResponse(HttpResponse<String> response) throws EntityNotFoundException, UnauthorizedException, BadRequestException, RateLimitException {
        if(response.statusCode() == HttpURLConnection.HTTP_OK) return;
        SpotifyApiErrorResponse errorResponse = globalMapper.map(response.body(),SpotifyApiErrorResponse.class);
        String errorMessage = errorResponse.getError().getMessage();
        switch (response.statusCode()){
            case HttpURLConnection.HTTP_UNAUTHORIZED :
                throw new UnauthorizedException(errorMessage);
            case HttpURLConnection.HTTP_BAD_REQUEST:
                throw new BadRequestException(errorMessage);
            case HttpURLConnection.HTTP_NOT_FOUND:
                throw new EntityNotFoundException(errorMessage);
            case 429:
                throw new RateLimitException(RATE_LIMIT_EXCEPTION_MESSAGE);
            default:
                break;
        }
    }

}
