package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static com.github.gmarcg00.spotify.utils.TestHelper.assertNotNullFields;
import static org.junit.jupiter.api.Assertions.*;

class GetArtistIntegrationTest {

    private static final String URL = "http://localhost:8080/artists";

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
    void testGetArtistExpiredAccessToken(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",401,"artist/get_artist_without_permissions.json");
        String path = URL.concat("/4aawyAB9vmqN3uQ7FjRGTy");


        //When && Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> executor.get(path,"expired", ArtistResponse.class));
        assertEquals("The access token expired",exception.getMessage());
    }

    @Test
    void testGetArtistNotFound(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRhhh",404,"artist/get_artist_not_found.json");
        String path = URL.concat("/4aawyAB9vmqN3uQ7FjRhhh");

        //When && Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> executor.get(path,"token", ArtistResponse.class));
        assertEquals("Resource not found",exception.getMessage());
    }

    @Test
    void testGetArtistSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",200,"artist/get_artist_successfully.json");
        String path = URL.concat("/4aawyAB9vmqN3uQ7FjRGTy");

        //When
        ArtistResponse result = executor.get(path,"token", ArtistResponse.class);

        //Then
        assertNotNull(result);
        assertNotNullFields(result);
    }
}
