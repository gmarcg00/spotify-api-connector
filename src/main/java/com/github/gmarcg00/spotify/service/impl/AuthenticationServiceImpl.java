package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.AccessToken;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.AuthenticationMapper;
import com.github.gmarcg00.spotify.external.api.model.response.authentication.AccessTokenResponse;
import com.github.gmarcg00.spotify.service.AuthenticationService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.github.gmarcg00.spotify.config.Config.ACCESS_TOKEN_PATH;

/**
 * @author Guillermo Marcos García
 * <p> Implementation of AuthenticationService interface </p>
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Executor executor;

    public AuthenticationServiceImpl(Executor executor){
        this.executor = executor;
    }

    @Override
    public AccessToken generateAccessToken(String clientId, String clientSecret) throws BadRequestException {
        String body = "grant_type=" + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8)
                + "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);
        AccessTokenResponse response = executor.post(ACCESS_TOKEN_PATH,body, AccessTokenResponse.class);
        return AuthenticationMapper.toEntity(response);
    }
}
