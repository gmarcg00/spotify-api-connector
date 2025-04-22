package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Playlist;
import com.github.gmarcg00.spotify.exception.*;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing playlists </p>
 */
public interface PlaylistService {

    /**
     * Returns a {@link Playlist Playlist} owned by a Spotify user
     *
     * @param id playlist identifier
     * @param token access token to retrieve Spotify API data
     *
     * @return {@link Playlist Playlist} the playlist requested
     *
     * @throws EntityNotFoundException if no playlist is found
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty
     * @throws BadRequestException if playlist identifier has an incorrect format
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id or token are null
     */
    Playlist getPlaylist(String id, String token) throws SpotifyApiException;
}
