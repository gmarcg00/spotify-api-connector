package com.github.gmarcg00.spotify.service;

import com.github.gmarcg00.spotify.TestCommonData;
import com.github.gmarcg00.spotify.data.Episode;
import com.github.gmarcg00.spotify.exception.BadRequestException;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeListResponse;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeResponse;
import com.github.gmarcg00.spotify.service.impl.EpisodeServiceImpl;
import com.github.gmarcg00.spotify.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceTest {

    private final String domain = "https://api.spotify.com/v1/episodes";

    private EpisodeService service;

    @Mock
    private Executor executor;

    @BeforeEach
    public void setUp(){service =  new EpisodeServiceImpl(executor);}

    @Test
    void testGetEpisodeSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        String path = domain.concat("/7eLcDZDYHXZCebtQmVFL25");
        when(executor.get(path,"", EpisodeResponse.class)).thenReturn(TestCommonData.getEpisodeResponse());

        //When
        Episode result = service.getEpisode("7eLcDZDYHXZCebtQmVFL25","");

        //Then
        assertEquals("La Puentecilla",result.getName());
        verify(executor,times(1)).get(path,"", EpisodeResponse.class);
    }

    @Test
    void testGetEpisodesSuccessfully() throws UnauthorizedException, EntityNotFoundException, BadRequestException {
        //Given
        String[] ids = new String[1];
        ids[0] = "7eLcDZDYHXZCebtQmVFL25";
        String path = TestHelper.buildSimpleGetListUri(domain,ids);
        EpisodeListResponse response = new EpisodeListResponse(List.of(TestCommonData.getEpisodeResponse()));
        when(executor.get(path,"", EpisodeListResponse.class)).thenReturn(response);

        //When
        List<Episode> result = service.getEpisodes(ids,"");

        //Then
        assertEquals(1,result.size());
        assertEquals("La Puentecilla",result.get(0).getName());
        verify(executor,times(1)).get(path,"", EpisodeListResponse.class);
    }
}
