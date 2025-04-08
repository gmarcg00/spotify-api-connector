package com.github.gmarcg00.spotify.integration.album;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
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

    private static final String URL = "http://localhost:8080/albums";

    private static WireMockServer wireMockServer;
    private AlbumService service;

    @BeforeAll
    static void startWiremock(){
        wireMockServer = getServer();
        wireMockServer.start();
        Config.ALBUMS_PATH = URL;
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
    void testGetAlbumNotFound(){
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTyA",400,"album/get_album_not_found.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getAlbum("4aawyAB9vmqN3uQ7FjRGTyA","token"));
        assertEquals("Invalid base62 id", exception.getMessage());
    }

    @Test
    void testGetAlbumSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTy",200,"album/get_album_successfully.json");

        //When
        Album response = service.getAlbum("4aawyAB9vmqN3uQ7FjRGTy","token");

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }

}
