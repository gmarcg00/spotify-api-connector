package com.github.gmarcg00.spotify.external.api.model.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyAuthErrorResponse {
    private String error;
    @JsonProperty(value = "error_description")
    private String errorDescription;
}
