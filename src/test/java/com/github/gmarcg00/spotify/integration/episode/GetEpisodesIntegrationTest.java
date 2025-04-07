package com.github.gmarcg00.spotify.integration.episode;

import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeListResponse;
import com.github.gmarcg00.spotify.utils.TestHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetEpisodesIntegrationTest {

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
        executor = new Executor();
    }

    @Test
    void testGetEpisodesNotFound(){
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQK,5TrEALrPu0wjmaoUyYmENjx",400,"episode/get_episodes_not_found.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQK";
        ids[1] = "5TrEALrPu0wjmaoUyYmENjx";
        String path = TestHelper.buildSimpleGetListUri(URL,ids);

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> executor.get(path,"", EpisodeListResponse.class));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetEpisodesSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQK,5TrEALrPu0wjmaoUyYmENj",200,"episode/get_episodes_successfully.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQK";
        ids[1] = "5TrEALrPu0wjmaoUyYmENj";
        String path = TestHelper.buildSimpleGetListUri(URL,ids);

        //When
        EpisodeListResponse response = executor.get(path,"token",EpisodeListResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }
}
