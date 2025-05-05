package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.data.Track;
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

class GetArtistTopElementsIntegrationTest {

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
    void testGetArtistTopTracksWithNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class, () -> service.getArtistTopTracks(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetArtistTopTracksFailedAuth(){
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL21/top-tracks",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getArtistTopTracks("7eLcDZDYHXZCebtQmVFL21","invalid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetArtistTopTracksInvalidIdFormat(){
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL2/top-tracks",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getArtistTopTracks("7eLcDZDYHXZCebtQmVFL2", "token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetArtistTopTracksIdNotFound(){
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL22/top-tracks",404,"generic/resource_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getArtistTopTracks("7eLcDZDYHXZCebtQmVFL22", "token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetArtistTopTracksSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL25/top-tracks",200,"artist/track/get_artist_top_tracks_successfully.json");

        //When
        List<Track> response = service.getArtistTopTracks("7eLcDZDYHXZCebtQmVFL25","token");

        //Then
        assertNotNull(response);
        for(Track track : response.stream().toList()){
            assertNotNullFields(track);
        }
    }

}
