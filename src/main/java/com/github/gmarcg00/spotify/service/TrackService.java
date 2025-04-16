package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.*;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing tracks </p>
 */
public interface TrackService {

    /**
     * Returns a {@link Track Track} which corresponds with the id
     *
     * @param id track identifier
     * @param token access token to retrieve Spotify API data
     *
     * @return {@link Track Track} the track requested
     *
     * @throws EntityNotFoundException if the track is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     * @throws BadRequestException if track identifier has an incorrect format
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id or token are null
     */
    Track getTrack(String id, String token) throws SpotifyApiException;

    /**
     * Returns a list of {@link Track Tracks} corresponding to the provided identifiers.
     *
     * @param ids an array of track identifiers
     * @param token access token to retrieve Spotify API data
     *
     * @return a list of {@link Track Tracks} corresponding to the requested identifiers
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     * @throws BadRequestException if track identifier has an incorrect format
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if ids or token are null
     */
    List<Track> getTracks(String[] ids, String token) throws SpotifyApiException;

}
