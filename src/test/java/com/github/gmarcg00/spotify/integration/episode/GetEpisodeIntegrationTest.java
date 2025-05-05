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

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetEpisodeIntegrationTest {

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
        Exception exception = assertThrows(NullPointerException.class, () -> service.getEpisode(null,null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetEpisodeFailedAuth(){
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENv",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getEpisode("5TrEALrPu0wjmaoUyYmENv","invalid_token"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetEpisodeInvalidId(){
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENjss",400,"generic/invalid_resource_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.getEpisode("5TrEALrPu0wjmaoUyYmENjss","token"));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetEpisodeNotFound(){
        //Given
        mockGetRequest("/episodes/5TrEALrPu0wjmaoUyYmENg",404,"generic/resource_not_found.json");

        //When && Then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getEpisode("5TrEALrPu0wjmaoUyYmENg","token"));
        assertEquals("Resource not found", exception.getMessage());
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
