package com.github.gmarcg00.spotify.integration.album;

import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.AlbumService;
import com.github.gmarcg00.spotify.service.impl.AlbumServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static org.junit.jupiter.api.Assertions.*;

class GetAlbumTracksIntegrationTest {

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
    void testGetAlbumTracksWithNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class , () -> service.getAlbumTracks(null,"","",null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetAlbumTracksFailedAuth(){
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Bj/tracks",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getAlbumTracks("5VaYKNDJhjfWtghV9UL1Bj","","","invalid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetAlbumTracksRateLimitExceeded(){
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Bs/tracks",429,"generic/rate_limit_exceeded.json");

        //When && Then
        Exception exception = assertThrows(RateLimitException.class, () -> service.getAlbumTracks("5VaYKNDJhjfWtghV9UL1Bs","","","token"));
        assertEquals("Rate limit exceeded. Please try it again later.",exception.getMessage());
    }

    @Test
    void testGetAlbumTracksInvalidIdFormat(){
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1B/tracks",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getAlbumTracks("5VaYKNDJhjfWtghV9UL1B","","","token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetAlbumTracksIdNotFound(){
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Ba/tracks",404,"generic/resource_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getAlbumTracks("5VaYKNDJhjfWtghV9UL1Ba","","","token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetAlbumTracksInvalidLimit(){
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Ba/tracks?limit=0",400,"album/get_album_tracks_invalid_limit.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class , () -> service.getAlbumTracks("5VaYKNDJhjfWtghV9UL1Ba","0","","token"));
        assertEquals("Invalid limit", exception.getMessage());
    }

    @Test
    void testGetAlbumTracksInvalidOffset(){
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Ba/tracks?offset=-1",400,"album/get_album_tracks_invalid_offset.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class , () -> service.getAlbumTracks("5VaYKNDJhjfWtghV9UL1Ba","","-1","token"));
        assertEquals("Invalid offset", exception.getMessage());
    }

    @Test
    void testGetAlbumTracksSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/albums/5VaYKNDJhjfWtghV9UL1Bj/tracks",200,"album/get_album_tracks_successfully.json");

        //When
        List<Track> response = service.getAlbumTracks("5VaYKNDJhjfWtghV9UL1Bj","","","token");

        //Then
        assertNotNull(response);
        assertEquals(7,response.size());
    }
}
