package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.data.other.AlbumType;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.ArtistMapper;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistAlbumsResponse;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistTopTracksResponse;
import com.github.gmarcg00.spotify.service.ArtistService;

import java.util.Arrays;
import java.util.List;

import static com.github.gmarcg00.spotify.config.Config.ARTISTS_PATH;
import static com.github.gmarcg00.spotify.service.utils.BuildUriHelper.buildSimpleGetListUri;
import static com.github.gmarcg00.spotify.service.utils.ServiceUtils.addQueryParams;


/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of ArtistService interface </p>
 */
public class ArtistServiceImpl implements ArtistService {

    private final Executor executor;

    public ArtistServiceImpl(Executor executor){
        this.executor = executor;
    }

    @Override
    public Artist getArtist(String id, String token) throws SpotifyApiException {
        String path = String.join("/",ARTISTS_PATH,id);
        ArtistResponse response = executor.get(path,token,ArtistResponse.class);
        return ArtistMapper.toEntity(response);
    }

    @Override
    public List<Artist> getArtists(String[] ids, String token) throws SpotifyApiException {
        String path = buildSimpleGetListUri(ARTISTS_PATH,ids);
        ArtistListResponse response = executor.get(path,token, ArtistListResponse.class);
        return response.getArtists().stream()
                .map(ArtistMapper::toEntity)
                .toList();
    }

    @Override
    public List<Album> getArtistAlbums(String id, AlbumType[] albumTypes, String limit, String offset, String token) throws SpotifyApiException{
        String path = String.join("/", ARTISTS_PATH,id,"albums");
        String types = String.join(",", Arrays.stream(albumTypes)
                .map(AlbumType::name)
                .map(String::toLowerCase)
                .toList());
        path = addQueryParams(path,limit,offset,types);
        ArtistAlbumsResponse response = executor.get(path,token,ArtistAlbumsResponse.class);
        return response.getItems().stream()
                .map(ArtistMapper::toEntity)
                .toList();
    }

    @Override
    public List<Track> getArtistTopTracks(String id, String token) throws SpotifyApiException {
        String path = String.join("/", ARTISTS_PATH,id,"top-tracks");
        ArtistTopTracksResponse response = executor.get(path,token,ArtistTopTracksResponse.class);
        return response.getTracks().stream()
                .map(ArtistMapper::toEntity)
                .toList();
    }
}
