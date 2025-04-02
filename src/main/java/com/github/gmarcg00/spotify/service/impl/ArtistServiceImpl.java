package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.ArtistMapper;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.gmarcg00.spotify.service.ArtistService;

import java.util.List;


/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of ArtistService interface </p>
 */
public class ArtistServiceImpl implements ArtistService {

    private final Executor<ArtistResponse> executor;

    public ArtistServiceImpl(Executor<ArtistResponse> executor){
        this.executor = executor;
    }

    @Override
    public Artist getArtist(String id, String token) throws EntityNotFoundException, UnauthorizedException {
        ArtistResponse responseModel = executor.get(id,"",token, ArtistResponse.class);
        return ArtistMapper.toEntity(responseModel);
    }

    @Override
    public List<Artist> getArtists(String[] ids, String token) throws EntityNotFoundException, UnauthorizedException {
        List<ArtistResponse> responseModel =  executor.gets(ids,token);
        return ArtistMapper.toEntityList(responseModel);
    }
}
