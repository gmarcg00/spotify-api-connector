package com.github.gmarcg00.spotify.exception;

public class EntityNotFoundException extends SpotifyApiException {
    public EntityNotFoundException(String message){
        super(message);
    }
}
