package com.github.gmarcg00.spotify.exception;

public class RateLimitException extends SpotifyApiException{

    public RateLimitException(String message){
        super(message);
    }
}
