package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackResponse;
import com.github.gmarcg00.spotify.utils.TestHelper;
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
        executor = new Executor();}

    @Test
    void testGetTrackSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/tracks/1zTzz7nUxA2UxE6NhNTWSF",200,"track/get_track_successfully.json");
        String path = URL.concat("/1zTzz7nUxA2UxE6NhNTWSF");


        //When
        TrackResponse response = executor.get(path,"token", TrackResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }

    @Test
    void testGetTracksSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/tracks?ids=2HHr7vMPAD2kOL599AvQep,1zTzz7nUxA2UxE6NhNTWSF",200,"track/get_tracks_successfully.json");
        String[] ids = new String[2];
        ids[0]="2HHr7vMPAD2kOL599AvQep";
        ids[1]="1zTzz7nUxA2UxE6NhNTWSF";
        String path = TestHelper.buildSimpleGetListUri(URL,ids);

        //When
        TrackListResponse response = executor.get(path,"token", TrackListResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
