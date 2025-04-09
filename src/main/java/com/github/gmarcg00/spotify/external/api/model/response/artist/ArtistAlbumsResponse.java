package com.github.gmarcg00.spotify.external.api.model.response.artist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ArtistAlbumsResponse {
    private String href;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private List<AlbumItem> items;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class AlbumItem {
        @JsonProperty(value = "album_type")
        private String albumType;
        @JsonProperty(value = "total_tracks")
        private int totalTracks;
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
        @JsonProperty(value = "album_group")
        private String albumGroup;


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
    }
}
