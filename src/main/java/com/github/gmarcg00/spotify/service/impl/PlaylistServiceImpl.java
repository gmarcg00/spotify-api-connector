package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.data.Playlist;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.mapper.PlaylistMapper;
import com.github.gmarcg00.spotify.external.api.model.response.playlist.PlaylistResponse;
import com.github.gmarcg00.spotify.service.PlaylistService;
import com.github.gmarcg00.spotify.service.utils.ServiceUtils;

import static com.github.gmarcg00.spotify.config.Config.PLAYLISTS_PATH;


/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of PlaylistService interface </p>
 */
public class PlaylistServiceImpl implements PlaylistService {

    private final Executor executor;

    public PlaylistServiceImpl(Executor executor){this.executor = executor;}

    @Override
    public Playlist getPlaylist(String id, String token) throws SpotifyApiException {
        ServiceUtils.checkNullValues(id,token);
        String path = String.join("/",PLAYLISTS_PATH,id);
        PlaylistResponse response = executor.get(path,token, PlaylistResponse.class);
        return PlaylistMapper.toEntity(response);
    }
}
