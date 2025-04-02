package com.github.gmarcg00.spotify.external.api.model.response.album;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.Copyright;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Image;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        AlbumResponse response = (AlbumResponse) object;
        return albumType.equals(response.getAlbumType()) &&
                totalTracks == response.getTotalTracks() &&
                (availableMarkets == null || availableMarkets.equals(response.getAvailableMarkets())) &&
                externalUrl.equals(response.getExternalUrl()) &&
                href.equals(response.getHref()) &&
                id.equals(response.getId()) &&
                images.equals(response.getImages()) &&
                name.equals(response.getName()) &&
                releaseDate.equals(response.getReleaseDate()) &&
                type.equals(response.getType()) &&
                artists.equals(response.getArtists()) &&
                tracks.equals(response.getTracks()) &&
                copyrights.equals(response.getCopyrights()) &&
                label.equals(response.getLabel()) &&
                popularity == response.getPopularity();
    }

    @Override
    public int hashCode(){
        return Objects.hash(id,href,name,type);
    }

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

        @Override
        public boolean equals(Object object){
            if(this == object) return true;
            if(object == null || getClass() != object.getClass()) return false;
            Artist artist = (Artist) object;
            return externalUrl.equals(artist.getExternalUrl()) &&
                    href.equals(artist.getHref()) &&
                    id.equals(artist.getId()) &&
                    name.equals(artist.getName()) &&
                    type.equals(artist.getType()) &&
                    uri.equals(artist.getUri());
        }

        @Override
        public int hashCode(){
            return Objects.hash(externalUrl,href,id,name,type,uri);
        }
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

            @Override
            public boolean equals(Object object){
                if(this == object) return true;
                if(object == null || getClass() != object.getClass()) return false;
                TrackItem trackItem = (TrackItem) object;
                return artists.equals(trackItem.getArtists()) &&
                        (availableMarkets == null || availableMarkets.equals(trackItem.getAvailableMarkets())) &&
                        discNumber == trackItem.getDiscNumber() &&
                        duration == trackItem.getDuration() &&
                        explicit == trackItem.isExplicit() &&
                        externalUrl.equals(trackItem.getExternalUrl()) &&
                        href.equals(trackItem.getHref()) &&
                        id.equals(trackItem.getId()) &&
                        name.equals(trackItem.getName()) &&
                        trackNumber == trackItem.getTrackNumber() &&
                        type.equals(trackItem.getType()) &&
                        uri.equals(trackItem.getUri());
            }

            @Override
            public int hashCode(){
                return Objects.hash(id,href,name,trackNumber);
            }

        }

        @Override
        public boolean equals(Object object){
            if(this == object) return true;
            if(object == null || getClass() != object.getClass()) return false;
            Tracks tracks = (Tracks) object;
            return href.equals(tracks.getHref()) &&
                    (next == null || next.equals(tracks.getNext())) &&
                    (previous == null || previous.equals(tracks.getPrevious())) &&
                    total == tracks.getTotal() &&
                    items.equals(tracks.getItems());
        }

        @Override
        public int hashCode(){
            return Objects.hash(href,total,items);
        }
    }
}
