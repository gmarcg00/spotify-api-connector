package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.ArtistService;
import com.github.gmarcg00.spotify.service.impl.ArtistServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetArtistIntegrationTest {

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
    void testGetArtistExpiredAccessToken(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",401,"artist/get_artist_without_permissions.json");

        //When && Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> service.getArtist("4aawyAB9vmqN3uQ7FjRGTy","token"));
        assertEquals("The access token expired",exception.getMessage());
    }

    @Test
    void testGetArtistNotFound(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRhhh",404,"artist/get_artist_not_found.json");

        //When && Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.getArtist("4aawyAB9vmqN3uQ7FjRhhh","token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetArtistSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",200,"artist/get_artist_successfully.json");

        //When
        Artist result = service.getArtist("4aawyAB9vmqN3uQ7FjRGTy","token");

        //Then
        assertNotNull(result);
        assertNotNullFields(result);
    }
}
