package com.github.gmarcg00.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Track {
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
    private int popularity;
    @JsonProperty(value = "track_number")
    private int trackNumber;
    private String type;
    private String uri;
}
