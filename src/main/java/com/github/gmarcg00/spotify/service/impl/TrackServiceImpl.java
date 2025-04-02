package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.TrackQueryParams;
import com.github.gmarcg00.spotify.service.TrackService;

import java.util.List;

/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of TrackService interface </p>
 */
public class TrackServiceImpl implements TrackService {

    private final Executor<Track> executor;

    public TrackServiceImpl(){
        this.executor = new Executor<>(API_HOST + TRACKS_ENDPOINT);
    }

    @Override
    public Track getTrack(String id,String market, String token) throws EntityNotFoundException, UnauthorizedException {
        return executor.get(id,market,token,Track.class);
    }

    @Override
    public List<Track> getTracks(TrackQueryParams params, String token) throws EntityNotFoundException {
        return executor.gets(params.getIds(),token);
    }
}
