package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.TrackMapper;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackResponse;
import com.github.gmarcg00.spotify.service.TrackService;

import java.util.List;

import static com.github.gmarcg00.spotify.config.Config.TRACKS_PATH;
import static com.github.gmarcg00.spotify.service.utils.BuildUriHelper.buildSimpleGetListUri;

/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of TrackService interface </p>
 */
public class TrackServiceImpl implements TrackService {

    private final Executor executor;

    public TrackServiceImpl(Executor executor){
        this.executor = executor;
    }

    @Override
    public Track getTrack(String id, String token) throws EntityNotFoundException, UnauthorizedException, BadRequestException {
        String path = String.join("/",TRACKS_PATH,id);
        TrackResponse response = executor.get(path,token,TrackResponse.class);
        return TrackMapper.toEntity(response);
    }

    @Override
    public List<Track> getTracks(String[] ids, String token) throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        String path = buildSimpleGetListUri(TRACKS_PATH,ids);
        TrackListResponse response = executor.get(path,token, TrackListResponse.class);
        return response.getTracks().stream()
                .map(TrackMapper::toEntity)
                .toList();
    }
}
