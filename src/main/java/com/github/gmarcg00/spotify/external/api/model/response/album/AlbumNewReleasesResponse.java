package com.github.gmarcg00.spotify.external.api.model.response.album;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlbumNewReleasesResponse {

    private Albums albums;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Albums {
        private String href;
        private List<AlbumResponse> items;
        private int limit;
        private String next;
        private int offset;
        private String previous;
        private int total;
    }
}
