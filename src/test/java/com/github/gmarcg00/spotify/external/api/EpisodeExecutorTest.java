package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpisodeExecutorTest {

    private static final String URL = "http://localhost:8080/episodes";

    private static WireMockServer wireMockServer;
    private Executor<EpisodeResponse> episodeExecutor;

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
    void setUp(){episodeExecutor = new Executor<>(URL);}

    @Test
    void testGetEpisodeSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENj",200,"episode/get_episode_successfully.json");

        //When
        EpisodeResponse response = episodeExecutor.get("5TrEALrPu0wjmaoUyYmENj","token", EpisodeResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
