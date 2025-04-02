package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.TestCommonData;
import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;
import com.github.gmarcg00.spotify.service.impl.AlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    private AlbumService service;

    @Mock
    private Executor<AlbumResponse> executor;

    @BeforeEach
    public void setUp(){
        service = new AlbumServiceImpl(executor);
    }

    @Test
    void testGetAlbumSuccessfully() throws ParseException, UnauthorizedException, EntityNotFoundException {
        //Given
        when(executor.get("64vx3cUb97lQGlgt8zozWL","","",AlbumResponse.class)).thenReturn(TestCommonData.getAlbumResponse());

        //When
        Album result = service.getAlbum("64vx3cUb97lQGlgt8zozWL","","");

        //Then
        assertEquals("Paris",result.getName());
        verify(executor,times(1)).get("64vx3cUb97lQGlgt8zozWL","","", AlbumResponse.class);

    }
}
