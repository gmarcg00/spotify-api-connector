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

import java.util.List;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetTracksIntegrationTest {

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
        Exception exception = assertThrows(NullPointerException.class, () -> service.getTracks(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetTracksFailedAuth(){
        //Given
        mockGetRequest("/tracks?ids=1zTzz7nUxA2UxE6NhNTWSH,5Cbo7oz78gqkzV3EAM63VH",401,"generic/failed_auth.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSH";
        ids[1]="5Cbo7oz78gqkzV3EAM63VH";

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class , ()-> service.getTracks(ids,"invalid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetTracksRateLimitExceeded(){
        //Given
        mockGetRequest("/tracks?ids=1zTzz7nUxA2UxE6NhNTWSM,5Cbo7oz78gqkzV3EAM63VM",429,"generic/rate_limit_exceeded.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSM";
        ids[1]="5Cbo7oz78gqkzV3EAM63VM";

        //When && Then
        Exception exception = assertThrows(RateLimitException.class, () -> service.getTracks(ids,"token"));
        assertEquals("Rate limit exceeded. Please try it again later.",exception.getMessage());
    }

    @Test
    void testGetTracksInvalidIdFormat(){
        //Given
        mockGetRequest("/tracks?ids=1zTzz7nUxA2UxE6NhNTWSF,5Cbo7oz78gqkzV3EAM63V",400,"generic/invalid_resource_id.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSF";
        ids[1]="5Cbo7oz78gqkzV3EAM63V";

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, ()  -> service.getTracks(ids,"token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetTracksIdNotFound() throws SpotifyApiException {
        //Given
        mockGetRequest("/tracks?ids=1zTzz7nUxA2UxE6NhNTWSF,5Cbo7oz78gqkzV3EAM63VS",200,"track/get_tracks_id_not_found.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSF";
        ids[1]="5Cbo7oz78gqkzV3EAM63VS";

        //When
        List<Track> response = service.getTracks(ids,"token");

        //Then
        assertEquals(1,response.size());
        assertNotNull(response.get(0));
    }

    @Test
    void testGetTracksIdsNotFound() throws SpotifyApiException {
        //Given
        mockGetRequest("/tracks?ids=1zTzz7nUxA2UxE6NhNTWSA,5Cbo7oz78gqkzV3EAM63VS",200,"track/get_tracks_ids_not_found.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSA";
        ids[1]="5Cbo7oz78gqkzV3EAM63VS";

        //When
        List<Track> response = service.getTracks(ids,"token");

        //Then
        assertEquals(0,response.size());
    }

    @Test
    void testGetTracksSuccessfully() throws SpotifyApiException {
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
