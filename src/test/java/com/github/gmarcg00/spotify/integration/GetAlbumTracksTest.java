package com.github.gmarcg00.spotify.integration;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumTracksResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetAlbumTracksTest {

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
        executor = new Executor();
    }

    @Test
    void testGetAlbumTracksInvalidLimit(){
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Bj/tracks?limit=0",400,"album/get_album_tracks_invalid_limit.json");
        String path = URL.concat("/5VaYKNDJhjfWtghV9UL1Bj/tracks?limit=0");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> executor.get(path,"token", AlbumTracksResponse.class));
        assertEquals("Entity with id: %s not found",exception.getMessage());
    }

    @Test
    void testGetAlbumTracksSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Bj/tracks",200,"album/get_album_tracks_successfully.json");
        String path = URL.concat("/5VaYKNDJhjfWtghV9UL1Bj/tracks");

        //When
        AlbumTracksResponse response = executor.get(path,"token", AlbumTracksResponse.class);

        //Then
        assertNotNull(response);
        for(AlbumTracksResponse.TrackItem item : response.getItems()){
            assertNotNullFields(item);
        }

    }
}
