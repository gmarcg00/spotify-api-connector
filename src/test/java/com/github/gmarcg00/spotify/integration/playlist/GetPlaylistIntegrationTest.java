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
import static org.junit.jupiter.api.Assertions.*;

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
    void testGetPlaylistNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class, () -> service.getPlaylist(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetPlaylistFailedAuth(){
        //Given
        mockGetRequest("/playlists/2nnfrwKp4u11FV28iMQxyX",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getPlaylist("2nnfrwKp4u11FV28iMQxyX","not valid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetPlaylistInvalidId(){
        //Given
        mockGetRequest("/playlists/2nnfrwKp4u11FV28iMQxy",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getPlaylist("2nnfrwKp4u11FV28iMQxy","token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetPlaylistNotFound(){
        //Given
        mockGetRequest("/playlists/2nnfrwKp4u11FV28iMQxyS",404,"generic/resource_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getPlaylist("2nnfrwKp4u11FV28iMQxyS","token"));
        assertEquals("Resource not found",exception.getMessage());
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
