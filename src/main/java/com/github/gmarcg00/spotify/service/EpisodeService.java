package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Episode;
import com.github.gmarcg00.spotify.exception.*;

import java.util.List;

/**
 * @author Guillermo Marcos García
 *
 * <p> Service for managing podcast episodes </p>
 */
public interface EpisodeService {

    /**
     * Returns a {@link Episode Episode} which corresponds with the id.
     *
     * @param id episode identifier
     * @param token access token to retrieve Spotify API data
     *
     * @return {@link Episode Episode} the episode requested
     *
     * @throws EntityNotFoundException if the episode is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     * @throws BadRequestException if episode identifier has an incorrect format
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id or token are null
     */
    Episode getEpisode(String id, String token) throws SpotifyApiException;

    /**
     * Returns a list of {@link Episode Episode} corresponding to the provided identifiers.
     *
     * @param ids an array of episode identifiers
     * @param token access token to retrieve Spotify API data
     *
     * @return a list of {@link Episode Episode} corresponding to the provided identifiers.
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     * @throws BadRequestException if episode identifier has an incorrect format
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if ids or token are null
     */
    List<Episode> getEpisodes(String[] ids,String token) throws SpotifyApiException;
}
