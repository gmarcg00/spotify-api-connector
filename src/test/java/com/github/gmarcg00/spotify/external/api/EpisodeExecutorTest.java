package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeResponse;
import com.github.gmarcg00.spotify.utils.TestHelper;
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
    void testGetEpisodeSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENj",200,"episode/get_episode_successfully.json");
        String path = URL.concat("/5TrEALrPu0wjmaoUyYmENj");


        //When
        EpisodeResponse response = executor.get(path,"token", EpisodeResponse.class);

        //Then
        assertNotNull(response);
        assertNotNullFields(response);
    }

    @Test
    void testGetEpisodesSuccessfully() throws UnauthorizedException, EntityNotFoundException {
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
