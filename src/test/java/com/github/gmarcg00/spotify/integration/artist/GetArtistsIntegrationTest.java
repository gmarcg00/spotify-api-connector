package com.github.gmarcg00.spotify.integration.artist;

import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistListResponse;
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

class GetArtistsIntegrationTest {

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
    void testGetArtistsNotFound(){
        //Given
        mockGetRequest("/artists?ids=4aawyAB9vmqN3uQ7FjRGTy,4aawyAB9vmqN3uQ7FjRhhh",400,"artist/get_artists_not_found.json");
        String[] ids = new String[2];
        ids[0]="4aawyAB9vmqN3uQ7FjRGTy";
        ids[1]="4aawyAB9vmqN3uQ7FjRhhh";
        String path = TestHelper.buildSimpleGetListUri(URL,ids);

        //When && Then
        Exception exception = assertThrows(BadRequestException.class, () -> executor.get(path,"token", ArtistListResponse.class));
        assertEquals("Invalid base62 id",exception.getMessage());
    }

    @Test
    void testGetArtistsSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        mockGetRequest("/artists?ids=7eLcDZDYHXZCebtQmVFL25,2CIMQHirSU0MQqyYHq0eOx",200,"artist/get_artists_successfully.json");
        String[] ids = new String[2];
        ids[0]="7eLcDZDYHXZCebtQmVFL25";
        ids[1]="2CIMQHirSU0MQqyYHq0eOx";
        String path = TestHelper.buildSimpleGetListUri(URL,ids);

        //When
        ArtistListResponse result = executor.get(path,"token", ArtistListResponse.class);

        //Then
        assertNotNull(result);
        assertNotNullFields(result);
    }
}
