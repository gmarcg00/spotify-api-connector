package com.github.gmarcg00.spotify.external.api.model.response.album;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.Copyright;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Image;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AlbumResponse {
    @JsonProperty(value = "album_type")
    private String albumType;
    @JsonProperty(value = "total_tracks")
    private int totalTracks;
    @JsonProperty(value = "is_playable")
    private boolean isPlayable;
    @JsonProperty(value = "available_markets")
    private List<String> availableMarkets;
    @JsonProperty(value = "external_urls")
    private ExternalUrl externalUrl;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    @JsonProperty(value = "release_date")
    private Date releaseDate;
    private String type;
    private String uri;
    private List<Artist> artists;
    private Tracks tracks;
    private List<Copyright> copyrights;
    private String label;
    private int popularity;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Tracks{
        private String href;
        private String next;
        private String previous;
        private int total;
        private List<TrackItem> items;

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
            @JsonProperty(value = "is_playable")
            private boolean isPlayable;
            private String name;
            @JsonProperty(value = "track_number")
            private int trackNumber;
            private String type;
            private String uri;
            @JsonProperty(value = "is_local")
            private boolean isLocal;
        }
    }
}
