package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.AlbumMapper;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;
import com.github.gmarcg00.spotify.service.AlbumService;

/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of AlbumService interface </p>
 */
public class AlbumServiceImpl implements AlbumService {

    private final Executor<AlbumResponse> executor;

    public AlbumServiceImpl(Executor<AlbumResponse> executor){
        this.executor = executor;
    }

    @Override
    public Album getAlbum(String id, String market, String token) throws EntityNotFoundException, UnauthorizedException {
        AlbumResponse responseModel = executor.get(id,market,token, AlbumResponse.class);
        return AlbumMapper.toEntity(responseModel);
    }
}
