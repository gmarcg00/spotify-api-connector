package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.AlbumMapper;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumNewReleasesResponse;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumTracksResponse;
import com.github.gmarcg00.spotify.service.AlbumService;
import com.github.gmarcg00.spotify.service.utils.ServiceUtils;

import java.util.List;
import java.util.Objects;

import static com.github.gmarcg00.spotify.config.Config.ALBUMS_NEW_RELEASES_PATH;
import static com.github.gmarcg00.spotify.config.Config.ALBUMS_PATH;
import static com.github.gmarcg00.spotify.service.utils.BuildUriHelper.buildSimpleGetListUri;
import static com.github.gmarcg00.spotify.service.utils.ServiceUtils.addQueryParams;

/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of AlbumService interface </p>
 */
public class AlbumServiceImpl implements AlbumService {

    private final Executor executor;

    public AlbumServiceImpl(Executor executor){
        this.executor = executor;
    }

    @Override
    public Album getAlbum(String id, String token) throws SpotifyApiException {
        ServiceUtils.checkNullValues(id,token);
        String path = String.join("/",ALBUMS_PATH,id);
        AlbumResponse response = executor.get(path,token,AlbumResponse.class);
        return AlbumMapper.toEntity(response);
    }

    @Override
    public List<Album> getAlbums(String[] ids, String token) throws SpotifyApiException {
        ServiceUtils.checkNullValues(ServiceUtils.combine(ids,token));
        String path = buildSimpleGetListUri(ALBUMS_PATH,ids);
        AlbumListResponse response = executor.get(path,token, AlbumListResponse.class);
        return response.getAlbums().stream()
                .filter(Objects::nonNull)
                .map(AlbumMapper::toEntity)
                .toList();
    }

    @Override
    public List<Track> getAlbumTracks(String id, String limit, String offset, String token) throws SpotifyApiException {
        ServiceUtils.checkNullValues(id,token);
        String path = String.join("/",ALBUMS_PATH,id,"tracks");
        path = addQueryParams(path,limit,offset);
        AlbumTracksResponse response = executor.get(path,token,AlbumTracksResponse.class);
        return response.getItems().stream()
                .map(AlbumMapper::toEntity)
                .toList();
    }

    @Override
    public List<Album> getNewReleases(String limit, String offset, String token) throws SpotifyApiException {
        ServiceUtils.checkNullValues(token);
        String path = addQueryParams(ALBUMS_NEW_RELEASES_PATH,limit,offset);
        AlbumNewReleasesResponse response = executor.get(path,token, AlbumNewReleasesResponse.class);
        return response.getAlbums().getItems().stream()
                .map(AlbumMapper::toEntity)
                .toList();
    }
}
