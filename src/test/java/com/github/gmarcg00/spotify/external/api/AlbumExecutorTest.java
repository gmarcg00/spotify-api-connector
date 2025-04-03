package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumListResponse;
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
        executor = new Executor(URL);
    }

    @Test
    void testGetAlbumNotFound(){
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTyA",400,"album/get_album_not_found.json");

        //When && Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> executor.get("4aawyAB9vmqN3uQ7FjRGTyA","token", AlbumResponse.class));
        assertEquals("Entity with id: 4aawyAB9vmqN3uQ7FjRGTyA not found", exception.getMessage());
    }

    @Test
    void testGetAlbumSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/albums/4aawyAB9vmqN3uQ7FjRGTy",200,"album/get_album_successfully.json");

        //When
        AlbumResponse response = executor.get("4aawyAB9vmqN3uQ7FjRGTy","token", AlbumResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }

    @Test
    void testGetAlbumsSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/albums?ids=64vx3cUb97lQGlgt8zozWL,7w1ESFMSHTpxQKlNLda9pt",200,"album/get_albums_successfully.json");
        String[] ids = new String[2];
        ids[0]="64vx3cUb97lQGlgt8zozWL";
        ids[1]="7w1ESFMSHTpxQKlNLda9pt";

        //When
        AlbumListResponse response = executor.gets(ids,"token", AlbumListResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
