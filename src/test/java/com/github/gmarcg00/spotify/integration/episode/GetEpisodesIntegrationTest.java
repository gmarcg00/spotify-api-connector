package com.github.gmarcg00.spotify.integration.episode;

import com.github.gmarcg00.spotify.config.Config;
import com.github.gmarcg00.spotify.data.Episode;
import com.github.gmarcg00.spotify.exception.*;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.EpisodeService;
import com.github.gmarcg00.spotify.service.impl.EpisodeServiceImpl;
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

class GetEpisodesIntegrationTest {

    private static final String URL = "http://localhost:8080/episodes";

    private static WireMockServer wireMockServer;
    private EpisodeService service;

    @BeforeAll
    static void startWiremock(){
        wireMockServer = getServer();
        wireMockServer.start();
        Config.EPISODES_PATH = URL;
    }

    @AfterAll
    static void stopWiremock(){
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp(){
       Executor executor = new Executor();
       service = new EpisodeServiceImpl(executor);
    }

    @Test
    void testGetEpisodesNotFound(){
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQK,5TrEALrPu0wjmaoUyYmENjx",400,"episode/get_episodes_not_found.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQK";
        ids[1] = "5TrEALrPu0wjmaoUyYmENjx";

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getEpisodes(ids,"token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetEpisodesSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQK,5TrEALrPu0wjmaoUyYmENj",200,"episode/get_episodes_successfully.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQK";
        ids[1] = "5TrEALrPu0wjmaoUyYmENj";

        //When
        List<Episode> response = service.getEpisodes(ids,"token");

        //Then
        assertNotNull(response);
        for(Episode episode : response.stream().toList()){
            assertNotNullFields(episode);
        }
    }
}
