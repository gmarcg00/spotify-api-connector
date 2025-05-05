package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.exception.RateLimitException;
import com.github.gmarcg00.spotify.exception.SpotifyApiException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing markets </p>
 */
public interface MarketService {

    /**
     * Returns the list of markets where Spotify is available.
     *
     * @param token access token to retrieve Spotify API data.
     *
     * @return a list of {@link String Market} where Spotify is available.
     *
     * @throws UnauthorizedException if token has expired, is invalid or is empty.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if token is null.
     */
    List<String> getMarkets(String token) throws SpotifyApiException;
}
