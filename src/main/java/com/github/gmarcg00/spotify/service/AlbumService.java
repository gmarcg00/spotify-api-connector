package com.github.gmarcg00.spotify.service;


import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing albums </p>
 */
public interface AlbumService {

    /**
     * Returns a {@link Album Album} which corresponds with the id.
     *
     * @param id album identifier
     * @param token access token to retrieve Spotify API data
     * @return {@link Album Album} the album requested
     * @throws EntityNotFoundException if the album is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     */
    Album getAlbum(String id, String token) throws EntityNotFoundException, UnauthorizedException;

    /**
     * Returns a list of {@link Album Album} corresponding to the provided identifiers.
     *
     * @param ids an array of album identifiers
     * @param token access token to retrieve Spotify API data
     * @return a list of {@link Album Album} corresponding to the provided identifiers.
     * @throws EntityNotFoundException if no {@link Album Album} is found
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     */
    List<Album> getAlbums(String[] ids, String token) throws EntityNotFoundException, UnauthorizedException;
}
