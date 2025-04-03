package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class AlbumExecutorTest {

    private static final String URL = "http://localhost:8080/albums";

    private static WireMockServer wireMockServer;
    private Executor<AlbumResponse> albumExecutor;

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
        albumExecutor = new Executor<>(URL);
    }

    @Test
    void testGetAlbumNotFound(){
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTyA",400,"album/get_album_not_found.json");

        //When && Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> albumExecutor.get("4aawyAB9vmqN3uQ7FjRGTyA","token", AlbumResponse.class));
        assertEquals("Entity with id: 4aawyAB9vmqN3uQ7FjRGTyA not found", exception.getMessage());
    }

    @Test
    void testGetAlbumSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTy",200,"album/get_album_successfully.json");

        //When
        AlbumResponse response = albumExecutor.get("4aawyAB9vmqN3uQ7FjRGTy","token", AlbumResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
