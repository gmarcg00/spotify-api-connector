package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.ArtistService;
import com.github.gmarcg00.spotify.service.impl.ArtistServiceImpl;
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

class GetArtistsIntegrationTest {

    private static final String URL = "http://localhost:8080/artists";

    private static WireMockServer wireMockServer;
    private ArtistService service;

    @BeforeAll
    static void startWiremock(){
        wireMockServer = getServer();
        wireMockServer.start();
        Config.ARTISTS_PATH = URL;
    }

    @AfterAll
    static void stopWiremock(){
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp(){
        Executor executor = new Executor();
        service = new ArtistServiceImpl(executor);
    }

    @Test
    void testGetArtistsWithNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class, () -> service.getArtist(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetArtistsFailedAuth(){
        //Given
        mockGetRequest("/artists?ids=1zTzz7nUxA2UxE6NhNTWSH,5Cbo7oz78gqkzV3EAM63VH",401,"generic/failed_auth.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSH";
        ids[1]="5Cbo7oz78gqkzV3EAM63VH";

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getArtists(ids,"invalid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetArtistsInvalidIdFormat(){
        //Given
        mockGetRequest("/artists?ids=1zTzz7nUxA2UxE6NhNTWSF,5Cbo7oz78gqkzV3EAM63V",400,"generic/invalid_resource_id.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSF";
        ids[1]="5Cbo7oz78gqkzV3EAM63V";

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getArtists(ids,"token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetArtistsIdsNotFound() throws SpotifyApiException {
        //Given
        mockGetRequest("/artists?ids=1zTzz7nUxA2UxE6NhNTWSA,5Cbo7oz78gqkzV3EAM63VS",200,"artist/get_artists_ids_not_found.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSA";
        ids[1]="5Cbo7oz78gqkzV3EAM63VS";

        //When
        List<Artist> response = service.getArtists(ids,"token");

        //Then
        assertEquals(0,response.size());
    }


    @Test
    void testGetArtistsSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/artists?ids=7eLcDZDYHXZCebtQmVFL25,2CIMQHirSU0MQqyYHq0eOx",200,"artist/get_artists_successfully.json");
        String[] ids = new String[2];
        ids[0]="7eLcDZDYHXZCebtQmVFL25";
        ids[1]="2CIMQHirSU0MQqyYHq0eOx";

        //When
        List<Artist> result = service.getArtists(ids,"token");

        //Then
        assertNotNull(result);
        for(Artist artist: result.stream().toList()){
            assertNotNullFields(artist);
        }
    }
}
