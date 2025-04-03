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

    /**
     * Returns a {@link Track Track} which corresponds with the id
     *
     * @param id track identifier
     * @param token access token to retrieve Spotify API data
     * @return {@link Track Track} the track requested
     * @throws EntityNotFoundException if the track is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     */
    Track getTrack(String id, String token) throws EntityNotFoundException, UnauthorizedException;

    /**
     * Returns a list of {@link Track Tracks} corresponding to the provided identifiers.
     *
     * @param ids an array of track identifiers
     * @param token access token to retrieve Spotify API data
     * @return a list of {@link Track Tracks} corresponding to the requested identifiers
     * @throws EntityNotFoundException if no track is found
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     */
    List<Track> getTracks(String[] ids, String token) throws UnauthorizedException, EntityNotFoundException;

}
