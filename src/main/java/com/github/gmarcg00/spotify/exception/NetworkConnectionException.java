package com.github.gmarcg00.spotify.exception;

public class NetworkConnectionException extends RuntimeException{
    public NetworkConnectionException (String message){
        super(message);
    }
}
