package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing artists </p>
 */
public interface ArtistService {

    String API_HOST = "https://api.spotify.com/v1";
    String ARTISTS_ENDPOINT = "/artists";

    /**
     * Returns an {@link Artist Artist} which corresponds with the id
     * @param id artist identifier
     * @param token access token to retrieve Spotify API data
     * @return {@link Artist Artist} the artist requested
     * @throws EntityNotFoundException if the artist is not found
     * @throws UnauthorizedException if token has expired, is invalid or is empty
     */
    Artist getArtist(String id, String token) throws EntityNotFoundException, UnauthorizedException;
    List<Artist> getArtists(String[] ids, String token) throws EntityNotFoundException, UnauthorizedException;
}
