package com.github.gmarcg00.spotify.external.api.model.response.artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArtistListResponse {
    private List<ArtistResponse> artists;
}
