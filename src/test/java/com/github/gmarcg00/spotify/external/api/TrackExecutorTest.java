package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrackExecutorTest {

    private static final String URL = "http://localhost:8080/tracks";

    private static WireMockServer wireMockServer;
    private Executor<TrackResponse> trackExecutor;

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
    void setUp(){trackExecutor = new Executor<>(URL);}

    @Test
    void testGetTrackSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSF",200,"track/get_track_successfully.json");

        //When
        TrackResponse response = trackExecutor.get("1zTzz7nUxA2UxE6NhNTWSF","token", TrackResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
