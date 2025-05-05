package com.github.gmarcg00.spotify.external.api.model.response.track;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrackListResponse {
    private List<TrackResponse> tracks;
}
