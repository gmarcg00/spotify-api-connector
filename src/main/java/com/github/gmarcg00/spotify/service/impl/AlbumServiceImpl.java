package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.AlbumMapper;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;
import com.github.gmarcg00.spotify.service.AlbumService;

import java.util.List;

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
    public Album getAlbum(String id, String token) throws EntityNotFoundException, UnauthorizedException {
        AlbumResponse responseModel = executor.get(id,token,AlbumResponse.class);
        return AlbumMapper.toEntity(responseModel);
    }

    @Override
    public List<Album> getAlbums(String[] ids, String token) throws EntityNotFoundException, UnauthorizedException {
        AlbumListResponse response = executor.gets(ids,token, AlbumListResponse.class);
        return response.getAlbums().stream()
                .map(AlbumMapper::toEntity)
                .toList();
    }
}
