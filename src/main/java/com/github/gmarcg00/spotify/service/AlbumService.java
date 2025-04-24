package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.*;

import java.util.List;

/**
 * @author Guillermo Marcos García
 * <p> Service for managing albums </p>
 */
public interface AlbumService {

    /**
     * Returns a {@link Album Album} which corresponds with the id.
     *
     * @param id album identifier.
     * @param token access token to retrieve Spotify API data.
     *
     * @return {@link Album Album} the album requested.
     *
     * @throws EntityNotFoundException if the album is not found.
     * @throws UnauthorizedException if token has expired, is invalid or is empty.
     * @throws BadRequestException if album identifier has an incorrect format.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id or token are null.
     */
    Album getAlbum(String id, String token) throws SpotifyApiException;

    /**
     * Returns a list of {@link Album Album} corresponding to the provided identifiers.
     *
     * @param ids an array of album identifiers.
     * @param token access token to retrieve Spotify API data.
     *
     * @return a list of {@link Album Album} corresponding to the provided identifiers.
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty.
     * @throws BadRequestException if an album identifier has an incorrect format.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if ids or token are null.
     */
    List<Album> getAlbums(String[] ids, String token) throws SpotifyApiException;

    /**
     * Returns a list of {@link Track Track} from the requested {@link Album album}.
     *
     * @param id {@link Album Album} identifier.
     * @param limit the maximum number of items to return. Default: 20, Minimum: 1, Maximum: 50. If not specified, pass {@code null} or an empty string.
     * @param offset the index of the first item to return. Default: 0. If not specified, pass {@code null} or an empty string.
     * @param token access token to retrieve Spotify API data.
     *
     * @return a list of {@link Track Track} from the requested {@link Album album}
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty.
     * @throws BadRequestException if album identifier has an incorrect format or the limit or offset are not allowed.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if id or token are null.
     */
    List<Track> getAlbumTracks(String id, String limit, String offset, String token) throws SpotifyApiException;

    /**
     * Returns a list of the latest {@link Album Album}
     *
     * @param limit the maximum number of items to return. Default: 20, Minimum: 1, Maximum: 50. If not specified, pass {@code null} or an empty string.
     * @param offset the index of the first item to return. Default: 0. If not specified, pass {@code null} or an empty string.
     * @param token access token to retrieve Spotify API data.
     *
     * @return a list of the latest {@link Album Album}
     *
     * @throws UnauthorizedException if the token has expired, is invalid, or is empty.
     * @throws BadRequestException if limit or offset values are not allowed.
     * @throws RateLimitException if the app exceeds the rate limit. Spotify's API rate limit is calculated based on the number of calls that your app makes to Spotify in a rolling 30-second window.
     * @throws NullPointerException if token is null.
     */
    List<Album> getNewReleases(String limit, String offset, String token) throws SpotifyApiException;
}
