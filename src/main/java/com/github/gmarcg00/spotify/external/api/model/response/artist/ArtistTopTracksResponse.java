package com.github.gmarcg00.spotify.external.api.model.response.artist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArtistTopTracksResponse {

    private List<TrackItem> tracks;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TrackItem {
        private Album album;
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
        @JsonProperty(value = "is_local")
        private boolean isLocal;
        private String name;
        private int popularity;
        private int trackNumber;
        private String type;
        private String uri;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class Album {
            @JsonProperty(value = "album_type")
            private String albumType;
            private List<Artist> artists;
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
            @JsonProperty(value = "total_tracks")
            private int totalTracks;
            private String type;
            private String uri;
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
}
