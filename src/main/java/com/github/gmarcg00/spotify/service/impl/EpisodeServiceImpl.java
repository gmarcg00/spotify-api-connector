package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Episode;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.EpisodeMapper;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeResponse;
import com.github.gmarcg00.spotify.service.EpisodeService;

/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of EpisodeService interface </p>
 */
public class EpisodeServiceImpl implements EpisodeService {

    private final Executor<EpisodeResponse> executor;

    public EpisodeServiceImpl(Executor<EpisodeResponse> executor){this.executor = executor;}

    @Override
    public Episode getEpisode(String id, String token) throws EntityNotFoundException, UnauthorizedException {
        EpisodeResponse responseModel = executor.get(id,token, EpisodeResponse.class);
        return EpisodeMapper.toEntity(responseModel);
    }
}
