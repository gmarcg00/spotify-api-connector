package com.github.gmarcg00.spotify.integration.track;

import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackResponse;
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
    private Executor executor;

    @BeforeAll
    static void startWiremock(){
        wireMockServer = getServer();
        wireMockServer.start();
    }

    @AfterAll
    static void stopWiremock(){
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp(){
        executor = new Executor();
    }

    @Test
    void testGetTrackFailedAuth(){
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSFA",401,"generic/failed_auth.json");
        String path = URL.concat("/1zTzz7nUxA2UxE6NhNTWSFA");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> executor.get(path,"", TrackResponse.class));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetTrackNotFound(){
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSFs",400,"track/get_track_not_found.json");
        String path = URL.concat("/1zTzz7nUxA2UxE6NhNTWSFs");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> executor.get(path,"", TrackResponse.class));
        assertEquals("Invalid base62 id",exception.getMessage());
    }


    @Test
    void testGetTrackSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSF",200,"track/get_track_successfully.json");
        String path = URL.concat("/1zTzz7nUxA2UxE6NhNTWSF");


        //When
        TrackResponse response = executor.get(path,"token", TrackResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
