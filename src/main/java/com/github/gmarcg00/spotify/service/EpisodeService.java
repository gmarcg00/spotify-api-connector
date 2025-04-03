package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Episode;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing podcast episodes </p>
 */
public interface EpisodeService {

    /**
     * Returns a {@link Episode Episode} which corresponds with the id
     * @param id episode identifier
     * @param token access token to retrieve Spotify API data
     * @return {@link Episode Episode} the episode requested
     * @throws EntityNotFoundException if the episode is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     */
    Episode getEpisode(String id, String token) throws EntityNotFoundException, UnauthorizedException;

    /**
     * Returns a list of {@link Episode Episode} corresponding to the provided identifiers
     *
     * @param ids an array of episode identifiers
     * @param token access token to retrieve Spotify API data
     * @return a list of {@link Episode Episode} corresponding to the provided identifiers.
     * @throws EntityNotFoundException if no {@link Episode Episode} is found
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     */
    List<Episode> getEpisodes(String[] ids,String token) throws EntityNotFoundException, UnauthorizedException;
}
