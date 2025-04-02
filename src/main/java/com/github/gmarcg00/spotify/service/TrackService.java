package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing tracks </p>
 */
public interface TrackService {

    String API_HOST = "https://api.spotify.com/v1";
    String TRACKS_ENDPOINT = "/tracks";

    /**
     * Returns a {@link Track Track} which corresponds with the id
     * @param id track identifier
     * @param market An ISO 3166-1 alpha-2 country code. If a country code is specified, only content that is available in that market will be returned.
     * @param token access token to retrieve Spotify API data
     * @return {@link Track Track} the track requested
     * @throws EntityNotFoundException if the track is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     */
    Track getTrack(String id, String market, String token) throws EntityNotFoundException, UnauthorizedException;

    /**
     * Returns a list of {@link Track Track}
     * @param params {@link TrackQueryParams TrackQueryParams} tracks query params
     * @param token access token to retrieve Spotify API data
     * @return a {@link Track Track} list
     * @throws EntityNotFoundException if no track is found
     */
    List<Track> getTracks(TrackQueryParams params, String token) throws EntityNotFoundException;
}
