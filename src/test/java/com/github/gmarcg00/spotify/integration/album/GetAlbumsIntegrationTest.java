package com.github.gmarcg00.spotify.integration.album;

import com.github.gmarcg00.spotify.config.Config;
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

import java.util.List;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetAlbumsIntegrationTest {

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
    void testGetAlbumsWithNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class , () -> service.getAlbums(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetAlbumsFailedAuth(){
        //Given
        mockGetRequest("/albums?ids=1zTzz7nUxA2UxE6NhNTWSH,5Cbo7oz78gqkzV3EAM63VH",401,"generic/failed_auth.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSH";
        ids[1]="5Cbo7oz78gqkzV3EAM63VH";

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getAlbums(ids,"token"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetAlbumsRateLimitExceeded(){
        //Given
        mockGetRequest("/albums?ids=1zTzz7nUxA2UxE6NhNTWSM,5Cbo7oz78gqkzV3EAM63VM",429,"generic/rate_limit_exceeded.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSM";
        ids[1]="5Cbo7oz78gqkzV3EAM63VM";

        //When && Then
        Exception exception = assertThrows(RateLimitException.class, () -> service.getAlbums(ids,"token"));
        assertEquals("Rate limit exceeded. Please try it again later.",exception.getMessage());
    }

    @Test
    void testGetAlbumsInvalidIdFormat(){
        //Given
        mockGetRequest("/albums?ids=64vx3cUb97lQGlgt8zozWLx,7w1ESFMSHTpxQKlNLda9pt",400,"generic/invalid_resource_id.json");
        String[] ids = new String[2];
        ids[0]="64vx3cUb97lQGlgt8zozWLx";
        ids[1]="7w1ESFMSHTpxQKlNLda9pt";

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getAlbums(ids,"token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetAlbumsIdsNotFound() throws SpotifyApiException {
        //Given
        mockGetRequest("/albums?ids=1zTzz7nUxA2UxE6NhNTWSF,5Cbo7oz78gqkzV3EAM63VS",200,"album/get_albums_ids_not_found.json");
        String[] ids = new String[2];
        ids[0]="1zTzz7nUxA2UxE6NhNTWSF";
        ids[1]="5Cbo7oz78gqkzV3EAM63VS";

        //When
        List<Album> response = service.getAlbums(ids,"token");

        //Then
        assertEquals(0,response.size());
    }

    @Test
    void testGetAlbumsSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/albums?ids=64vx3cUb97lQGlgt8zozWL,7w1ESFMSHTpxQKlNLda9pt",200,"album/get_albums_successfully.json");
        String[] ids = new String[2];
        ids[0]="64vx3cUb97lQGlgt8zozWL";
        ids[1]="7w1ESFMSHTpxQKlNLda9pt";

        //When
        List<Album> response = service.getAlbums(ids,"token");

        //Then
        assertNotNull(response);
        for(Album album : response.stream().toList()){
            assertNotNullFields(album);
        }
    }
}
