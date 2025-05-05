package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.other.AlbumType;
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
import static org.junit.jupiter.api.Assertions.*;

class GetArtistAlbumsIntegrationTest {

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
    void testGetArtistAlbumsWithNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class, () -> service.getArtistAlbums(null,new AlbumType[0],"","",null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetArtistAlbumsFailedAuth(){
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL29/albums",401,"generic/failed_auth.json");

        //When && Then
        Exception exception =  assertThrows(UnauthorizedException.class, () -> service.getArtistAlbums("7eLcDZDYHXZCebtQmVFL29",new AlbumType[0],"","",""));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetArtistAlbumsInvalidIdFormat(){
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL2/albums",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class , () -> service.getArtistAlbums("7eLcDZDYHXZCebtQmVFL2",new AlbumType[0],"","","token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetArtistAlbumsArtistNotFound(){
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL24/albums",404,"generic/resource_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getArtistAlbums("7eLcDZDYHXZCebtQmVFL24", new AlbumType[]{},"","","token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetArtistAlbumsDefaultRequest() throws SpotifyApiException {
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL25/albums",200,"artist/album/get_artist_albums_default_request.json");

        //When
        List<Album> response = service.getArtistAlbums("7eLcDZDYHXZCebtQmVFL25", new AlbumType[]{},"","","token");

        //Then
        assertNotNull(response);
        assertEquals(20,response.size());
    }

    @Test
    void testGetArtistAlbumsCustomRequest() throws SpotifyApiException {
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL25/albums?limit=30&offset=2&include_groups=album,single",200,"artist/album/get_artist_albums_custom_request.json");
        AlbumType[] albumTypes = new AlbumType[2];
        albumTypes[0] = AlbumType.ALBUM;
        albumTypes[1] = AlbumType.SINGLE;

        //When
        List<Album> response = service.getArtistAlbums("7eLcDZDYHXZCebtQmVFL25",albumTypes,"30","2","token");

        //Then
        assertNotNull(response);
        assertEquals(30,response.size());
    }
}
