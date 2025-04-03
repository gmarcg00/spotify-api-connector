package com.github.gmarcg00.spotify.external.api;

import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistListResponse;
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

class ArtistExecutorTest {

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
        executor = new Executor(URL);
    }

    @Test
    void testGetArtistWithoutPermissions(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",401,"artist/get_artist_without_permissions.json");

        //When && Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> executor.get("4aawyAB9vmqN3uQ7FjRGTy","expired", ArtistResponse.class));
        assertEquals("INVALID_ACCESS_TOKEN",exception.getMessage());
    }

    @Test
    void testGetArtistNotFound(){
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRhhh",404,"artist/get_artist_not_found.json");

        //When && Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> executor.get("4aawyAB9vmqN3uQ7FjRhhh","token", ArtistResponse.class));
        assertEquals("Entity with id: 4aawyAB9vmqN3uQ7FjRhhh not found",exception.getMessage());
    }

    @Test
    void testGetArtistSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/artists/4aawyAB9vmqN3uQ7FjRGTy",200,"artist/get_artist_successfully.json");

        //When
        ArtistResponse result = executor.get("4aawyAB9vmqN3uQ7FjRGTy","token", ArtistResponse.class);

        //Then
        assertNotNull(result);
        assertNotNullFields(result);
    }

    @Test
    void testGetArtistsSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        mockGetRequest("/artists?ids=7eLcDZDYHXZCebtQmVFL25,2CIMQHirSU0MQqyYHq0eOx",200,"artist/get_artists_successfully.json");
        String[] ids = new String[2];
        ids[0]="7eLcDZDYHXZCebtQmVFL25";
        ids[1]="2CIMQHirSU0MQqyYHq0eOx";

        //When
        ArtistListResponse result = executor.gets(ids,"token", ArtistListResponse.class);

        //Then
        assertNotNull(result);
        assertNotNullFields(result);
    }
}
