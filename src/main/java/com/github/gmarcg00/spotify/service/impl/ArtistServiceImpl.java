package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.ArtistMapper;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.gmarcg00.spotify.service.ArtistService;

import java.util.List;

import static com.github.gmarcg00.spotify.service.utils.BuildUriHelper.buildSimpleGetListUri;


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
    public Artist getArtist(String id, String token) throws EntityNotFoundException, UnauthorizedException, BadRequestException {
        String path = String.join("/",ARTISTS_PATH,id);
        ArtistResponse response = executor.get(path,token,ArtistResponse.class);
        return ArtistMapper.toEntity(response);
    }

    @Override
    public List<Artist> getArtists(String[] ids, String token) throws EntityNotFoundException, UnauthorizedException, BadRequestException {
        String path = buildSimpleGetListUri(ARTISTS_PATH,ids);
        ArtistListResponse response = executor.get(path,token, ArtistListResponse.class);
        return response.getArtists().stream()
                .map(ArtistMapper::toEntity)
                .toList();
    }
}
