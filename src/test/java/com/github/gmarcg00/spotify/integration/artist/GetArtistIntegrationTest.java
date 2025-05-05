package com.github.gmarcg00.spotify.integration.artist;

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

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetArtistIntegrationTest {

    private static WireMockServer wireMockServer;
    private ArtistService service;

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
        Executor executor = new Executor();
        service = new ArtistServiceImpl(executor);
    }

    @Test
    void testGetArtistNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class , () -> service.getArtist(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetArtistFailedAuth(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",401,"generic/failed_auth.json");

        //When && Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> service.getArtist("4aawyAB9vmqN3uQ7FjRGTy","token"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetArtistInvalidId(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTyH",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class , () -> service.getArtist("4aawyAB9vmqN3uQ7FjRGTyH","token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetArtistNotFound(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRhhh",404,"generic/resource_not_found.json");

        //When && Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.getArtist("4aawyAB9vmqN3uQ7FjRhhh","token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetArtistSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTM",200,"artist/get_artist_successfully.json");

        //When
        Artist result = service.getArtist("4aawyAB9vmqN3uQ7FjRGTM","token");

        //Then
        assertNotNull(result);
        assertNotNullFields(result);
    }
}
