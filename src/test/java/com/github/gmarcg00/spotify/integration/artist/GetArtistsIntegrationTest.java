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
    void testGetArtistsNotFound(){
        //Given
        mockGetRequest("/artists?ids=4aawyAB9vmqN3uQ7FjRGTy,4aawyAB9vmqN3uQ7FjRhhh",400,"artist/get_artists_not_found.json");
        String[] ids = new String[2];
        ids[0]="4aawyAB9vmqN3uQ7FjRGTy";
        ids[1]="4aawyAB9vmqN3uQ7FjRhhh";

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getArtists(ids,"token"));
        assertEquals("Invalid base62 id",exception.getMessage());
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
