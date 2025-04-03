package com.github.gmarcg00.spotify.external.api.model.response.episode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EpisodeListResponse {
    private List<EpisodeResponse> episodes;
}
