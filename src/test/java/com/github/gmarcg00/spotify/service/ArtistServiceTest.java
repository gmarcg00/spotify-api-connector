package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.TestCommonData;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.gmarcg00.spotify.service.impl.ArtistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    private ArtistService service;

    @Mock
    private Executor executor;

    @BeforeEach
    public void setUp(){
        service = new ArtistServiceImpl(executor);
    }

    @Test
    void testGetArtistSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        when(executor.get("7eLcDZDYHXZCebtQmVFL25","", ArtistResponse.class)).thenReturn(TestCommonData.getArtistResponse());

        //When
        Artist result = service.getArtist("7eLcDZDYHXZCebtQmVFL25","");

        //Then
        assertEquals("Aitana",result.getName());
        verify(executor,times(1)).get("7eLcDZDYHXZCebtQmVFL25","", ArtistResponse.class);
    }
}
