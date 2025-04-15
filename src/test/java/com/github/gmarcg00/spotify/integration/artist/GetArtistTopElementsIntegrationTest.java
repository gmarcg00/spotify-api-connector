package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Track;
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
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetArtistTopElementsIntegrationTest {

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
    void testGetArtistTopTracksSuccessfully() throws UnauthorizedException, BadRequestException, EntityNotFoundException {
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
