package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.TestCommonData;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.gmarcg00.spotify.utils.MockHelper.getServer;
import static com.github.gmarcg00.spotify.utils.MockHelper.mockGetRequest;
import static org.junit.jupiter.api.Assertions.*;

class ArtistExecutorTest {

    private static final String URL = "http://localhost:8080/artists";

    private static WireMockServer wireMockServer;
    private Executor<ArtistResponse> artistExecutor;

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
        artistExecutor = new Executor<>(URL);
    }

    @Test
    void testGetArtistWithoutPermissions(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",401,"artist/get_artist_without_permissions.json");

        //When && Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> artistExecutor.get("4aawyAB9vmqN3uQ7FjRGTy","expired", ArtistResponse.class));
        assertEquals("INVALID_ACCESS_TOKEN",exception.getMessage());
    }

    @Test
    void testGetArtistNotFound(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRhhh",404,"artist/get_artist_not_found.json");

        //When && Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> artistExecutor.get("4aawyAB9vmqN3uQ7FjRhhh","token", ArtistResponse.class));
        assertEquals("Entity with id: 4aawyAB9vmqN3uQ7FjRhhh not found",exception.getMessage());
    }

    @Test
    void testGetArtistSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        ArtistResponse expected = TestCommonData.getArtistResponse();
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",200,"artist/get_artist_successfully.json");

        //When
        ArtistResponse result = artistExecutor.get("4aawyAB9vmqN3uQ7FjRGTy","token", ArtistResponse.class);

        //Then
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
