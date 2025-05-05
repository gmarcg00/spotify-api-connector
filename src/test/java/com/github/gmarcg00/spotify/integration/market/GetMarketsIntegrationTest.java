package com.github.gmarcg00.spotify.integration.market;

import com.github.gmarcg00.spotify.exception.SpotifyApiException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.MarketService;
import com.github.gmarcg00.spotify.service.impl.MarketServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static org.junit.jupiter.api.Assertions.*;

class GetMarketsIntegrationTest {

    private static WireMockServer wireMockServer;
    private MarketService service;

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
        service = new MarketServiceImpl(executor);
    }

    @Test
    void testGetMarketsWithNullToken(){
        //When && Then
        Exception exception = assertThrows(NullPointerException.class, () -> service.getMarkets(null));
        assertEquals("object must not be null",exception.getMessage());
    }

    @Test
    void testGetMarketsFailedAuth(){
        //Given
        mockGetRequest("/markets",401,"generic/failed_auth.json");

        //When && Then
        Exception exception = assertThrows(UnauthorizedException.class, () -> service.getMarkets("invalid"));
        assertEquals("No token provided",exception.getMessage());
    }

    @Test
    void testGetMarketsSuccessfully() throws SpotifyApiException {
        //Given
        mockGetRequest("/markets",200,"market/get_markets_successfully.json");

        //When
        List<String> response = service.getMarkets("token");

        //Then
        assertNotNull(response);
        assertEquals(185,response.size());
    }
}
