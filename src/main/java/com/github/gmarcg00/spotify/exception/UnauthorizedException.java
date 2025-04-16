package com.github.gmarcg00.spotify.exception;

public class UnauthorizedException extends SpotifyApiException{
    public UnauthorizedException(String message){
        super(message);
    }
}
