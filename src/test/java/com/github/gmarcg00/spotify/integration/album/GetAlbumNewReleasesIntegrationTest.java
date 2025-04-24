package com.github.gmarcg00.spotify.integration.album;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.SpotifyApiException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
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

class GetAlbumNewReleasesIntegrationTest {

    private static final String URL = "http://localhost:8080/browse/new-releases";

    private static WireMockServer wireMockServer;
    private AlbumService service;

    @BeforeAll
    static void startWiremock(){
        wireMockServer = getServer();
        wireMockServer.start();
        Config.ALBUMS_NEW_RELEASES_PATH = URL;
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
    void testGetAlbumNewReleasesNullParameters() {
        //When && Then
        Exception exception = assertThrows(NullPointerException.class , () -> service.getNewReleases(null,null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetAlbumNewReleasesFailedAuth(){
        //Given
        mockGetRequest("/browse/new-releases",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getNewReleases("","","invalid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetAlbumNewReleases() throws SpotifyApiException {
        //Given
        mockGetRequest("/browse/new-releases",200,"album/get_new_releases_successfully.json");

        //When
        List<Album> response = service.getNewReleases("","","token");

        //Then
        assertNotNull(response);
        assertEquals(20,response.size());
    }
}
