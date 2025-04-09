package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.data.other.AlbumType;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing artists </p>
 */
public interface ArtistService {

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

    /**
     * Returns a list of {@link Album Album} belonging to the corresponding artist.
     *
     * @param id artist identifier
     * @param albumTypes A comma-separated list of keywords that will be used to filter the response. If not supplied, all album types will be returned.
     * @param limit the maximum number of items to return. Default: 20, Minimum: 1, Maximum: 50.
     * @param offset the index of the first item to return. Default: 0.
     * @param token access token to retrieve Spotify API data
     * @return a list of {@link Album Album} belonging to the corresponding artist
     */
    List<Album> getArtistAlbums(String id, AlbumType[] albumTypes, String limit, String offset, String token) throws UnauthorizedException, BadRequestException, EntityNotFoundException;

    /**
     * Returns a list with the top {@link Track Track} of the artist
     *
     * @param id artist identifier
     * @param token access token to retrieve Spotify API data
     * @return the list with th top {@link Track Track} of the artist
     */
    List<Track> getArtistTopTracks(String id, String token) throws UnauthorizedException, BadRequestException, EntityNotFoundException;
}
