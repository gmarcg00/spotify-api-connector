package com.github.gmarcg00.spotify.exception;

public class BadRequestException extends SpotifyApiException{
    public BadRequestException(String message){
        super(message);
    }
}
