package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Episode;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.EpisodeMapper;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeResponse;
import com.github.gmarcg00.spotify.service.EpisodeService;

import java.util.List;

import static com.github.gmarcg00.spotify.config.Config.EPISODES_PATH;
import static com.github.gmarcg00.spotify.service.utils.BuildUriHelper.buildSimpleGetListUri;


/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of EpisodeService interface </p>
 */
public class EpisodeServiceImpl implements EpisodeService {

    private final Executor executor;

    public EpisodeServiceImpl(Executor executor){this.executor = executor;}

    @Override
    public Episode getEpisode(String id, String token) throws SpotifyApiException {
        String path = String.join("/",EPISODES_PATH,id);
        EpisodeResponse response = executor.get(path,token, EpisodeResponse.class);
        return EpisodeMapper.toEntity(response);
    }

    @Override
    public List<Episode> getEpisodes(String[] ids, String token) throws SpotifyApiException {
        String path = buildSimpleGetListUri(EPISODES_PATH,ids);
        EpisodeListResponse response = executor.get(path,token,EpisodeListResponse.class);
        return response.getEpisodes().stream()
                .map(EpisodeMapper::toEntity)
                .toList();
    }
}
