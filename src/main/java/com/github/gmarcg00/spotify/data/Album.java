package com.github.gmarcg00.spotify.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Album {
    private String albumType;
    private int totalTracks;
    private List<String> availableMarkets;
    private ExternalUrl externalUrl;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private Instant releaseDate;
    private String type;
    private String uri;
    private List<Artist> artists;
    private List<Track> tracks;
    private List<Copyright> copyrights;
    private int popularity;
}
