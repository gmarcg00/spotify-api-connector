package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Playlist;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing playlists </p>
 */
public interface PlaylistService {

    /**
     * Returns a {@link Playlist Playlist} which corresponds with the id
     *
     * @param id playlist identifier
     * @param token access token to retrieve Spotify API data
     * @return {@link Playlist Playlist} the playlist requested
     * @throws EntityNotFoundException if no playlist is found
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     */
    Playlist getPlaylist(String id, String token) throws EntityNotFoundException, UnauthorizedException, BadRequestException;
}
