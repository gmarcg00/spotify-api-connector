package com.github.gmarcg00.spotify.external.api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyApiErrorResponse {

    private Error error;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Error {
        private int status;
        private String message;
    }
}
