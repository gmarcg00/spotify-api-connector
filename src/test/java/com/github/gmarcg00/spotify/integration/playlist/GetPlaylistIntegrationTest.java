package com.github.gmarcg00.spotify.integration.playlist;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Playlist;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.PlaylistService;
import com.github.gmarcg00.spotify.service.impl.PlaylistServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetPlaylistIntegrationTest {

    private static final String URL = "http://localhost:8080/playlists";

    private static WireMockServer wireMockServer;
    private PlaylistService service;

    @BeforeAll
    static void startWiremock(){
        wireMockServer = getServer();
        wireMockServer.start();
        Config.PLAYLISTS_PATH = URL;
    }

    @AfterAll
    static void stopWiremock(){
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp(){
        Executor executor = new Executor();
        service = new PlaylistServiceImpl(executor);
    }

    @Test
    void testGetPlaylistSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/playlists/2nnfrwKp4u11FV28iMQxyV",200,"playlist/get_playlist_successfully.json");

        //When
        Playlist result = service.getPlaylist("2nnfrwKp4u11FV28iMQxyV","token");

        //Then
        assertNotNull(result);
    }
}
