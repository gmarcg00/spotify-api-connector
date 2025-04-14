package com.github.gmarcg00.spotify.external.api.model.response.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Follower;
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
public class PlaylistResponse {
    private boolean collaborative;
    private String description;
    @JsonProperty(value = "external_urls")
    private ExternalUrl externalUrl;
    private Follower followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private Owner owner;
    @JsonProperty(value = "primary_color")
    private String primaryColor;
    @JsonProperty(value = "public")
    private boolean isPublic;
    @JsonProperty(value = "snapshot_id")
    private String snapshotId;
    private Elements tracks;
    private String type;
    private String uri;


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Elements {
        private String href;
        private String next;
        private String previous;
        private int total;
        private int limit;
        private List<Item> items;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Item{
        @JsonProperty(value = "added_at")
        private Date addedAt;
        @JsonProperty(value = "is_local")
        private boolean local;
        @JsonProperty(value = "primary_color")
        private String primaryColor;
        private PlaylistElement track;


        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class PlaylistElement {
            @JsonProperty(value = "available_markets")
            private List<String> availableMarkets;
            private boolean explicit;
            private String type;
            private boolean episode;
            @JsonProperty(value = "track")
            private boolean isTrack;
            private Album album;
            private List<Artist> artists;
            @JsonProperty(value = "disc_number")
            private int discNumber;
            @JsonProperty(value = "track_number")
            private int trackNumber;
            @JsonProperty(value = "duration_ms")
            private long duration;
            @JsonProperty(value = "external_urls")
            private ExternalUrl externalUrl;
            private String href;
            private String id;
            private String name;
            private int popularity;
            private String uri;


            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            public static class Album {
                @JsonProperty(value = "available_markets")
                private List<String> availableMarkets;
                private String type;
                @JsonProperty(value = "album_type")
                private String albumType;
                private String href;
                private String id;
                private List<Image> images;
                private String name;
                @JsonProperty(value = "release_date")
                private Date releaseDate;
                private String uri;
                private List<Artist> artists;
                @JsonProperty(value = "external_urls")
                private ExternalUrl externalUrl;
                @JsonProperty(value = "total_tracks")
                private int totalTracks;
            }
        }


        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class Artist{
            @JsonProperty(value = "external_urls")
            private ExternalUrl externalUrl;
            private String href;
            private String id;
            private String name;
            private String type;
            private String uri;
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Owner {
        @JsonProperty(value = "display_name")
        private String displayName;
        @JsonProperty(value = "external_urls")
        private ExternalUrl externalUrl;
        private String href;
        private String id;
        private String type;
        private String uri;
    }
}
