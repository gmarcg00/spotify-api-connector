package com.github.gmarcg00.spotify.integration.track;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.TrackService;
import com.github.gmarcg00.spotify.service.impl.TrackServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetTrackIntegrationTest {

    private static final String URL = "http://localhost:8080/tracks";

    private static WireMockServer wireMockServer;
    private TrackService service;

    @BeforeAll
    static void startWiremock(){
        wireMockServer = getServer();
        wireMockServer.start();
        Config.TRACKS_PATH = URL;
    }

    @AfterAll
    static void stopWiremock(){
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp(){
       Executor executor = new Executor();
       service = new TrackServiceImpl(executor);
    }

    @Test
    void testNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class, () -> service.getTrack(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetTrackFailedAuth(){
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSN",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getTrack("1zTzz7nUxA2UxE6NhNTWSN","token"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetTrackInvalidId(){
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSFA",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getTrack("1zTzz7nUxA2UxE6NhNTWSFA","token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetTrackNotFound(){
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSFs",404,"track/get_track_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getTrack("1zTzz7nUxA2UxE6NhNTWSFs","token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetTrackSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSF",200,"track/get_track_successfully.json");

        //When
        Track response = service.getTrack("1zTzz7nUxA2UxE6NhNTWSF","token");

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
