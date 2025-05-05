package com.github.gmarcg00.spotify.integration.authentication;

import com.github.gmarcg00.spotify.data.AccessToken;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.service.AuthenticationService;
import com.github.gmarcg00.spotify.service.impl.AuthenticationServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockPostRequest;
import static org.junit.jupiter.api.Assertions.*;

class GenerateAccessTokenIntegrationTest {

    private static final String URL = "http://localhost:8080/api/token";

    private static WireMockServer wireMockServer;
    private AuthenticationService service;

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
        service = new AuthenticationServiceImpl(executor);
    }

    @Test
    void testGenerateAccessTokenInvalidClientId(){
        //Given
        String body = "grant_type=" + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8)
                + "&client_id=" + URLEncoder.encode("client_id_incorrect", StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode("client_secret", StandardCharsets.UTF_8);
        mockPostRequest("/api/token",400, Map.of("Content-Type","application/x-www-form-urlencoded"),body,"authentication/invalid_client_id.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.generateAccessToken("client_id_incorrect","client_secret"));
        assertEquals("Invalid client", exception.getMessage());
    }

    @Test
    void testGenerateAccessTokenInvalidClientSecret(){
        //Given
        String body = "grant_type=" + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8)
                + "&client_id=" + URLEncoder.encode("client_id", StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode("client_secret_incorrect", StandardCharsets.UTF_8);
        mockPostRequest("/api/token",400, Map.of("Content-Type","application/x-www-form-urlencoded"),body,"authentication/invalid_client_secret.json");

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> service.generateAccessToken("client_id","client_secret_incorrect"));
        assertEquals("Invalid client secret", exception.getMessage());
    }

    @Test
    void testGenerateAccessTokenSuccessfully() throws BadRequestException {
        //Given
        String body = "grant_type=" + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8)
                + "&client_id=" + URLEncoder.encode("client_id", StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode("client_secret", StandardCharsets.UTF_8);
        mockPostRequest("/api/token",200, Map.of("Content-Type","application/x-www-form-urlencoded"),body,"authentication/generate_access_token_successfully.json");

        //When
        AccessToken accessToken = service.generateAccessToken("client_id","client_secret");

        //Then
        assertNotNull(accessToken);
    }
}
