package com.github.gmarcg00.spotify.service;


import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing albums </p>
 */
public interface AlbumService {

    /**
     * Returns a {@link Album Album} which corresponds with the id
     * @param id album identifier
     * @param token access token to retrieve Spotify API data
     * @return {@link Album Album} the album requested
     * @throws EntityNotFoundException if the album is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     */
    Album getAlbum(String id, String token) throws EntityNotFoundException, UnauthorizedException;
}
