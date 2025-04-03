package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.TestCommonData;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackResponse;
import com.github.gmarcg00.spotify.service.impl.TrackServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    private TrackService service;

    @Mock
    private Executor executor;

    @BeforeEach
    public void setUp(){service = new TrackServiceImpl(executor);}

    @Test
    void testGetTrackSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        when(executor.get("4aawyAB9vmqN3uQ7FjRGTy","", TrackResponse.class)).thenReturn(TestCommonData.getTrackResponse());

        //When
        Track track = service.getTrack("4aawyAB9vmqN3uQ7FjRGTy","");

        //Then
        assertEquals("Paris",track.getName());
        verify(executor,times(1)).get("4aawyAB9vmqN3uQ7FjRGTy","", TrackResponse.class);
    }

    @Test
    void testGetTracksSuccessfully() throws UnauthorizedException, EntityNotFoundException {
        //Given
        String[] ids = new String[1];
        ids[0] = "7eLcDZDYHXZCebtQmVFL25";
        TrackListResponse response = new TrackListResponse(List.of(TestCommonData.getTrackResponse()));
        when(executor.gets(ids,"",TrackListResponse.class)).thenReturn(response);

        //When
        List<Track> result = service.getTracks(ids,"");

        //Then
        assertEquals(1,result.size());
        assertEquals("Paris",result.get(0).getName());
        verify(executor,times(1)).gets(ids,"", TrackListResponse.class);
    }
}
