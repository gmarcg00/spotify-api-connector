package com.github.gmarcg00.spotify.integration.episode;

import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeResponse;
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
    private Executor executor;

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
        executor = new Executor();}

    @Test
    void testGetEpisodeNotFound(){
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENjss",400,"episode/get_episode_not_found.json");
        String path = URL.concat("/5TrEALrPu0wjmaoUyYmENjss");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> executor.get(path,"token",EpisodeResponse.class));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetEpisodeSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENj",200,"episode/get_episode_successfully.json");
        String path = URL.concat("/5TrEALrPu0wjmaoUyYmENj");

        //When
        EpisodeResponse response = executor.get(path,"token", EpisodeResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
