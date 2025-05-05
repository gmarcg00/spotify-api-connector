package com.github.gmarcg00.spotify.integration.album;


import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.AlbumService;
import com.github.gmarcg00.spotify.service.impl.AlbumServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetAlbumIntegrationTest {

    private static WireMockServer wireMockServer;
    private AlbumService service;

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
       service = new AlbumServiceImpl(executor);
    }

    @Test
    void testGetAlbumWithNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class , () -> service.getAlbum(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetAlbumFailedAuth(){
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTyM",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class , () -> service.getAlbum("4aawyAB9vmqN3uQ7FjRGTyM","not valid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetAlbumInvalidId(){
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTy",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getAlbum("4aawyAB9vmqN3uQ7FjRGTy","token"));
        assertEquals("Invalid base62 id", exception.getMessage());
    }

    @Test
    void testGetAlbumNotFound(){
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTyS",404,"generic/resource_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getAlbum("4aawyAB9vmqN3uQ7FjRGTyS","token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetAlbumSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTyK",200,"album/get_album_successfully.json");

        //When
        Album response = service.getAlbum("4aawyAB9vmqN3uQ7FjRGTyK","token");

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }

}
