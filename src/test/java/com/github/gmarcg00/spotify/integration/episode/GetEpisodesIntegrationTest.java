package com.github.gmarcg00.spotify.integration.episode;

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

    private static WireMockServer wireMockServer;
    private EpisodeService service;

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
       Executor executor = new Executor();
       service = new EpisodeServiceImpl(executor);
    }

    @Test
    void testNullParameters(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class, () -> service.getEpisodes(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetEpisodesFailedAuth(){
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQA,5TrEALrPu0wjmaoUyYmENjA",401,"generic/failed_auth.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQA";
        ids[1] = "5TrEALrPu0wjmaoUyYmENjA";

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class , () -> service.getEpisodes(ids,"invalid token"));
        assertEquals("No token provided", exception.getMessage());
    }

    @Test
    void testGetEpisodesRateLimitExceeded(){
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQQ,5TrEALrPu0wjmaoUyYmENjQ",429,"generic/rate_limit_exceeded.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQQ";
        ids[1] = "5TrEALrPu0wjmaoUyYmENjQ";

        //When && Then
        Exception exception = assertThrows(RateLimitException.class , () -> service.getEpisodes(ids,"token"));
        assertEquals("Rate limit exceeded. Please try it again later.", exception.getMessage());
    }

    @Test
    void testGetEpisodesInvalidIdFormat(){
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQ,5TrEALrPu0wjmaoUyYmENjQ",400,"generic/invalid_resource_id.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQ";
        ids[1] = "5TrEALrPu0wjmaoUyYmENjQ";

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getEpisodes(ids,"token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetEpisodesIdsNotFound() throws SpotifyApiException {
        //Given
        mockGetRequest("/episodes?ids=4eIS8RhRNZXUTiyKL04cQS,5TrEALrPu0wjmaoUyYmENS",200,"episode/get_episodes_not_found.json");
        String [] ids = new String[2];
        ids[0] = "4eIS8RhRNZXUTiyKL04cQS";
        ids[1] = "5TrEALrPu0wjmaoUyYmENS";

        //When
        List<Episode> result = service.getEpisodes(ids,"token");

        //Then
        assertEquals(0,result.size());
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
