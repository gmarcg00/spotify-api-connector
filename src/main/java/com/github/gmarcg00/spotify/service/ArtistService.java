package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing artists </p>
 */
public interface ArtistService {

    String ARTISTS_PATH = "https://api.spotify.com/v1/artists";

    /**
     * Returns an {@link Artist Artist} which corresponds with the id.
     *
     * @param id artist identifier
     * @param token access token to retrieve Spotify API data
     * @return {@link Artist Artist} the artist requested
     * @throws EntityNotFoundException if the artist is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     */
    Artist getArtist(String id, String token) throws EntityNotFoundException, UnauthorizedException, BadRequestException;

    /**
     * Returns a list of {@link Artist Artist} corresponding to the provided identifiers.
     *
     * @param ids an array of artist identifiers
     * @param token access token to retrieve Spotify API data
     * @return a list of {@link Artist Artist} corresponding to the provided identifiers.
     * @throws EntityNotFoundException if no {@link Artist Artist} is found
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     */
    List<Artist> getArtists(String[] ids, String token) throws EntityNotFoundException, UnauthorizedException, BadRequestException;

}
