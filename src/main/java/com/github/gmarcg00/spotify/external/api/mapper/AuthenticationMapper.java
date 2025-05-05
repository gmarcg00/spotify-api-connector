package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.AccessToken;
import com.github.gmarcg00.spotify.external.api.model.response.authentication.AccessTokenResponse;

public class AuthenticationMapper {

    private AuthenticationMapper(){}

    public static AccessToken toEntity(AccessTokenResponse response){
        return AccessToken.builder()
                .accessToken(response.getAccessToken())
                .tokenType(response.getTokenType())
                .expiresIn(response.getExpiresIn())
                .build();
    }
}
