package com.github.gmarcg00.spotify.external.api.model.response.album;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlbumTracksResponse {
    private String href;
    private List<TrackItem> items;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class TrackItem{
        private List<Artist> artists;
        @JsonProperty(value = "available_markets")
        private List<String> availableMarkets;
        @JsonProperty(value = "disc_number")
        private int discNumber;
        @JsonProperty(value = "duration_ms")
        private long duration;
        private boolean explicit;
        @JsonProperty(value = "external_urls")
        private ExternalUrl externalUrl;
        private String href;
        private String id;
        private String name;
        @JsonProperty(value = "track_number")
        private int trackNumber;
        private String type;
        private String uri;
        @JsonProperty(value = "is_local")
        private boolean isLocal;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Artist {
        @JsonProperty(value = "external_urls")
        private ExternalUrl externalUrl;
        private String href;
        private String id;
        private String name;
        private String type;
        private String uri;
    }
}
