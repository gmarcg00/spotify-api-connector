package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.AccessToken;
import com.github.gmarcg00.spotify.exception.BadRequestException;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing authentication </p>
 */
public interface AuthenticationService {
    /**
     * Returns an {@link AccessToken AccessToken} to interact with Spotify API data
     *
     * @param clientId client identifier
     * @param clientSecret client secret
     * @return an {@link AccessToken AccessToken}
     * @throws BadRequestException if the clientId or clientSecret are incorrect
     */
    AccessToken generateAccessToken(String clientId, String clientSecret) throws BadRequestException;
}
