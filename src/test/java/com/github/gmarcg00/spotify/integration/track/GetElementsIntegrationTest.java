package com.github.gmarcg00.spotify.integration.track;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.TrackService;
import com.github.gmarcg00.spotify.service.impl.TrackServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetElementsIntegrationTest {

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
    void testGetTracksSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/tracks?ids=2HHr7vMPAD2kOL599AvQep,1zTzz7nUxA2UxE6NhNTWSF",200,"track/get_tracks_successfully.json");
        String[] ids = new String[2];
        ids[0]="2HHr7vMPAD2kOL599AvQep";
        ids[1]="1zTzz7nUxA2UxE6NhNTWSF";

        //When
        List<Track> response = service.getTracks(ids,"token");

        //Then
        assertNotNull(response);
        for(Track track: response.stream().toList()){
            assertNotNullFields(track);
        }
    }
}
