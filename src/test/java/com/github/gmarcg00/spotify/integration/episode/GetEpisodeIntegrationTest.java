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

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetEpisodeIntegrationTest {

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
    void testGetEpisodeNotFound(){
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENjss",400,"episode/get_episode_not_found.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getEpisode("5TrEALrPu0wjmaoUyYmENjss","token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetEpisodeSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENj",200,"episode/get_episode_successfully.json");

        //When
        Episode response = service.getEpisode("5TrEALrPu0wjmaoUyYmENj","");

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
