package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.other.AlbumType;
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

import java.util.List;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static org.junit.jupiter.api.Assertions.*;

class GetArtistAlbumsIntegrationTest {

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
    void testGetArtistAlbumsArtistNotFound(){
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL24/albums",404,"artist/album/artist_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getArtistAlbums("7eLcDZDYHXZCebtQmVFL24", new AlbumType[]{},"","","token"));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetArtistAlbumsDefaultRequest() throws UnauthorizedException, BadRequestException, EntityNotFoundException {
        //Given
        mockGetRequest("/artists/7eLcDZDYHXZCebtQmVFL25/albums",200,"artist/album/get_artist_albums_default_request.json");

        //When
        List<Album> response = service.getArtistAlbums("7eLcDZDYHXZCebtQmVFL25", new AlbumType[]{},"","","token");

        //Then
        assertNotNull(response);
        assertEquals(20,response.size());
    }

    @Test
    void testGetArtistAlbumsCustomRequest() throws UnauthorizedException, BadRequestException, EntityNotFoundException {
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
