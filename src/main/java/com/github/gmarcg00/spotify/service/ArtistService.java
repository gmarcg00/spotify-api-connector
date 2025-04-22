package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.data.other.AlbumType;
import com.github.gmarcg00.spotify.exception.*;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing artists </p>
 */
public interface ArtistService {

    /**
     * Returns an {@link Artist Artist} which corresponds with the id.
     *
     * @param id artist identifier.
     * @param token access token to retrieve Spotify API data.
     *
     * @return {@link Artist Artist} the artist requested.
     *
     * @throws EntityNotFoundException if the artist is not found.
     * @throws UnauthorizedException if token has expired, is invalid or is empty.
     * @throws BadRequestException if artist identifier has an incorrect format.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id or token are null.
     */
    Artist getArtist(String id, String token) throws SpotifyApiException;

    /**
     * Returns a list of {@link Artist Artist} corresponding to the provided identifiers.
     *
     * @param ids an array of artist identifiers.
     * @param token access token to retrieve Spotify API data.
     *
     * @return a list of {@link Artist Artist} corresponding to the provided identifiers.
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty.
     * @throws BadRequestException if an artist identifier has an incorrect format.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if ids or token are null.
     */
    List<Artist> getArtists(String[] ids, String token) throws SpotifyApiException;

    /**
     * Returns a list of {@link Album Album} belonging to the corresponding artist.
     *
     * @param id artist identifier
     * @param albumTypes A comma-separated list of keywords that will be used to filter the response. If not supplied, all album types will be returned. Pass an empty array to ignore the filter.
     * @param limit the maximum number of items to return. Default: 20, Minimum: 1, Maximum: 50. If not specified, pass {@code null} or an empty string.
     * @param offset the index of the first item to return. Default: 0. If not specified, pass {@code null} or an empty string.
     * @param token access token to retrieve Spotify API data
     *
     * @return a list of {@link Album Album} belonging to the corresponding artist
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty.
     * @throws EntityNotFoundException if the artist is not found.
     * @throws BadRequestException if artist identifier has an incorrect format.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id,token or albumTypes are null.
     */
    List<Album> getArtistAlbums(String id, AlbumType[] albumTypes, String limit, String offset, String token) throws SpotifyApiException;

    /**
     * Returns a list with the top {@link Track Track} of the artist
     *
     * @param id artist identifier
     * @param token access token to retrieve Spotify API data
     *
     * @return the list with th top {@link Track Track} of the artist
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty.
     * @throws EntityNotFoundException if the artist is not found.
     * @throws BadRequestException if artist identifier has an incorrect format.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id,token are null.
     */
    List<Track> getArtistTopTracks(String id, String token) throws SpotifyApiException;
}
